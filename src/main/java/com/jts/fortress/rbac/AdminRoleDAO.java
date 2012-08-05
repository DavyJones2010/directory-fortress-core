/*
 * Copyright (c) 2009-2012. Joshua Tree Software, LLC.  All Rights Reserved.
 */

package com.jts.fortress.rbac;

import com.jts.fortress.CreateException;
import com.jts.fortress.GlobalErrIds;
import com.jts.fortress.GlobalIds;
import com.jts.fortress.ObjectFactory;
import com.jts.fortress.cfg.Config;
import com.jts.fortress.FinderException;
import com.jts.fortress.RemoveException;
import com.jts.fortress.UpdateException;
import com.jts.fortress.ldap.DaoUtil;
import com.jts.fortress.ldap.PoolMgr;

import com.jts.fortress.util.time.CUtil;
import com.jts.fortress.util.attr.VUtil;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPAttribute;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPAttributeSet;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPConnection;
//import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPCompareAttrNames;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPEntry;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPException;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPModification;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPModificationSet;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPSearchResults;

import java.util.ArrayList;
import java.util.List;

/**
 * The AdminRoleDAO is called by {@link AdminRoleP} and processes data via its entity {@link AdminRole}.
 * <p/>
 * The Fortress AdminRoleDAO uses the following other Fortress structural and aux object classes:
 * <h4>1. ftRls Structural objectclass is used to store the AdminRole information like name, and temporal constraints</h4>
 * <ul>
 * <li>  ------------------------------------------
 * <li> <code>objectclass	( 1.3.6.1.4.1.38088.2.1</code>
 * <li> <code>NAME 'ftRls'</code>
 * <li> <code>DESC 'Fortress Role Object Class'</code>
 * <li> <code>SUP organizationalrole</code>
 * <li> <code>STRUCTURAL</code>
 * <li> <code>MUST ( ftId $ ftRoleName )</code>
 * <li> <code>MAY ( description $ ftCstr ) )</code>
 * <li>  ------------------------------------------
 * </ul>
 * <h4>2. ftProperties AUXILIARY Object Class is used to store client specific name/value pairs on target entity</h4>
 * <code># This aux object class can be used to store custom attributes.</code><br />
 * <code># The properties collections consist of name/value pairs and are not constrainted by Fortress.</code><br />
 * <ul>
 * <li>  ------------------------------------------
 * <li> <code>objectclass ( 1.3.6.1.4.1.38088.3.2</code>
 * <li> <code>NAME 'ftProperties'</code>
 * <li> <code>DESC 'Fortress Properties AUX Object Class'</code>
 * <li> <code>AUXILIARY</code>
 * <li> <code>MAY ( ftProps ) ) </code>
 * <li>  ------------------------------------------
 * </ul>
 * <h4>3. ftPools Auxiliary object class store the ARBAC Perm and User OU assignments on AdminRole entity</h4>
 * <ul>
 * <li>  ------------------------------------------
 * <li> <code>objectclass ( 1.3.6.1.4.1.38088.3.3</code>
 * <li> <code>NAME 'ftPools'</code>
 * <li> <code>DESC 'Fortress Pools AUX Object Class'</code>
 * <li> <code>AUXILIARY</code>
 * <li> <code>MAY ( ftOSU $ ftOSP ) )</code>
 * <li>  ------------------------------------------
 * </ul>
 * <h4>4. ftMods AUXILIARY Object Class is used to store Fortress audit variables on target entity</h4>
 * <ul>
 * <li> <code>objectclass ( 1.3.6.1.4.1.38088.3.4</code>
 * <li> <code>NAME 'ftMods'</code>
 * <li> <code>DESC 'Fortress Modifiers AUX Object Class'</code>
 * <li> <code>AUXILIARY</code>
 * <li> <code>MAY (</code>
 * <li> <code>ftModifier $</code>
 * <li> <code>ftModCode $</code>
 * <li> <code>ftModId ) )</code>
 * <li>  ------------------------------------------
 * </ul>
 * <p/>

 *
 * @author Shawn McKinney
 * @created November 13, 2009
 */
final class AdminRoleDAO

{
    private static final String CLS_NM = AdminRoleDAO.class.getName();
    private static final String ROLE_OCCUPANT = "roleOccupant";
    private static final String ROLE_OSP = "ftOSP";
    private static final String ROLE_OSU = "ftOSU";
    private static final String ROLE_RANGE = "ftRange";
    private static final String POOLS_AUX_OBJECT_CLASS_NAME = "ftPools";
    private static final String ADMIN_ROLE_OBJ_CLASS[] = {
        GlobalIds.TOP, GlobalIds.ROLE_OBJECT_CLASS_NM, GlobalIds.PROPS_AUX_OBJECT_CLASS_NAME, POOLS_AUX_OBJECT_CLASS_NAME, GlobalIds.FT_MODIFIER_AUX_OBJECT_CLASS_NAME
    };
    private static final String ROLE_NM = "ftRoleName";
    private static final String[] ROLE_NM_ATR = {
        ROLE_NM
    };

    private static final String[] ROLE_ATRS = {
        GlobalIds.FT_IID, ROLE_NM, GlobalIds.DESC, GlobalIds.CONSTRAINT, ROLE_OCCUPANT, ROLE_OSP, ROLE_OSU, ROLE_RANGE
    };

    AdminRoleDAO(){}

    /**
     * Create a new AdminRole entity using supplied data.  Required attribute is {@link AdminRole#name}.
     * This data will be stored in the {@link GlobalIds#ADMIN_ROLE_ROOT} container.
     *
     * @param entity record contains AdminRole data.  Null attributes will be ignored.
     * @return input record back to client.
     * @throws com.jts.fortress.CreateException in the event LDAP errors occur.
     */
    final AdminRole create(AdminRole entity)
        throws CreateException
    {
        LDAPConnection ld = null;
        String dn = getDn(entity);
        try
        {
            ld = PoolMgr.getConnection(PoolMgr.ConnType.ADMIN);
            LDAPAttributeSet attrs = new LDAPAttributeSet();
            attrs.add(DaoUtil.createAttributes(GlobalIds.OBJECT_CLASS, ADMIN_ROLE_OBJ_CLASS));
            entity.setId();
            attrs.add(DaoUtil.createAttribute(GlobalIds.FT_IID, entity.getId()));
            attrs.add(DaoUtil.createAttribute(ROLE_NM, entity.getName()));
            // description field is optional on this object class:
            if (VUtil.isNotNullOrEmpty(entity.getDescription()))
            {
                attrs.add(DaoUtil.createAttribute(GlobalIds.DESC, entity.getDescription()));
            }
            // CN attribute is required for this object class:
            attrs.add(DaoUtil.createAttribute(GlobalIds.CN, entity.getName()));
            attrs.add(DaoUtil.createAttribute(GlobalIds.CONSTRAINT, CUtil.setConstraint(entity)));
            DaoUtil.loadAttrs(entity.getOsP(), attrs, ROLE_OSP);
            DaoUtil.loadAttrs(entity.getOsU(), attrs, ROLE_OSU);
            String szRaw = entity.getRoleRangeRaw();
            if (VUtil.isNotNullOrEmpty(szRaw))
            {
                attrs.add(DaoUtil.createAttribute(ROLE_RANGE, szRaw));
            }
            LDAPEntry myEntry = new LDAPEntry(dn, attrs);
            DaoUtil.add(ld, myEntry, entity);
        }
        catch (LDAPException e)
        {
            String error = CLS_NM + ".create role [" + entity.getName() + "] caught LDAPException=" + e.getLDAPResultCode() + " msg=" + e.getMessage();
            throw new CreateException(GlobalErrIds.ARLE_ADD_FAILED, error, e);
        }
        finally
        {
            PoolMgr.closeConnection(ld, PoolMgr.ConnType.ADMIN);
        }
        return entity;
    }


    /**
     * Update existing AdminRole entity using supplied data.  Required attribute is {@link AdminRole#name}.
     * This data will be stored in the {@link GlobalIds#ADMIN_ROLE_ROOT} container.
     *
     * @param entity record contains AdminRole data.  Null attributes will be ignored.
     * @return input record back to client.
     * @throws UpdateException in the event LDAP errors occur.
     */
    final AdminRole update(AdminRole entity)
        throws UpdateException
    {
        LDAPConnection ld = null;
        String dn = getDn(entity);
        try
        {
            ld = PoolMgr.getConnection(PoolMgr.ConnType.ADMIN);
            LDAPModificationSet mods = new LDAPModificationSet();
            if (VUtil.isNotNullOrEmpty(entity.getDescription()))
            {
                LDAPAttribute desc = new LDAPAttribute(GlobalIds.DESC, entity.getDescription());
                mods.add(LDAPModification.REPLACE, desc);
            }
            if (VUtil.isNotNullOrEmpty(entity.getOccupants()))
            {
                for (String name : entity.getOccupants())
                {
                    LDAPAttribute occupant = new LDAPAttribute(ROLE_OCCUPANT, name);
                    mods.add(LDAPModification.ADD, occupant);
                }
            }
            if (entity.isTemporalSet())
            {
                String szRawData = CUtil.setConstraint(entity);
                if (VUtil.isNotNullOrEmpty(szRawData))
                {
                    LDAPAttribute constraint = new LDAPAttribute(GlobalIds.CONSTRAINT, szRawData);
                    mods.add(LDAPModification.REPLACE, constraint);
                }
            }
            DaoUtil.loadAttrs(entity.getOsU(), mods, ROLE_OSU);
            DaoUtil.loadAttrs(entity.getOsP(), mods, ROLE_OSP);
            String szRaw = entity.getRoleRangeRaw();
            if (VUtil.isNotNullOrEmpty(szRaw))
            {
                LDAPAttribute raw = new LDAPAttribute(ROLE_RANGE, szRaw);
                mods.add(LDAPModification.REPLACE, raw);
            }
            if (mods.size() > 0)
            {
                DaoUtil.modify(ld, dn, mods, entity);
            }
        }
        catch (LDAPException e)
        {
            String error = CLS_NM + ".update name [" + entity.getName() + "] caught LDAPException=" + e.getLDAPResultCode() + " msg=" + e.getMessage();
            throw new UpdateException(GlobalErrIds.ARLE_UPDATE_FAILED, error, e);
        }
        finally
        {
            PoolMgr.closeConnection(ld, PoolMgr.ConnType.ADMIN);
        }
        return entity;
    }


    /**
     * This method will add the supplied DN as a role occupant to the target record.
     * This data will be stored in the {@link GlobalIds#ADMIN_ROLE_ROOT} container.
     *
     * @param entity record contains {@link AdminRole#name}.  Null attributes will be ignored.
     * @param userDn contains the DN for userId who is being assigned.
     * @return input record back to client.
     * @throws UpdateException in the event LDAP errors occur.
     */
    final AdminRole assign(AdminRole entity, String userDn)
        throws UpdateException
    {
        LDAPConnection ld = null;
        String dn = getDn(entity);
        try
        {
            ld = PoolMgr.getConnection(PoolMgr.ConnType.ADMIN);
            LDAPModificationSet mods = new LDAPModificationSet();
            LDAPAttribute occupant = new LDAPAttribute(ROLE_OCCUPANT, userDn);
            mods.add(LDAPModification.ADD, occupant);
            DaoUtil.modify(ld, dn, mods, entity);
        }
        catch (LDAPException e)
        {
            String error = CLS_NM + ".assign role name [" + entity.getName() + "] user dn [" + userDn + "] caught LDAPException=" + e.getLDAPResultCode() + " msg=" + e.getMessage();
            throw new UpdateException(GlobalErrIds.ARLE_USER_ASSIGN_FAILED, error, e);
        }
        finally
        {
            PoolMgr.closeConnection(ld, PoolMgr.ConnType.ADMIN);
        }
        return entity;
    }


    /**
     * This method will remove the supplied DN as a role occupant to the target record.
     * This data will be stored in the {@link GlobalIds#ADMIN_ROLE_ROOT} container.
     *
     * @param entity record contains {@link AdminRole#name}.  Null attributes will be ignored.
     * @param userDn contains the DN for userId who is being deassigned.
     * @return input record back to client.
     * @throws UpdateException in the event LDAP errors occur.
     */
    final AdminRole deassign(AdminRole entity, String userDn)
        throws UpdateException
    {
        LDAPConnection ld = null;
        String dn = getDn(entity);
        try
        {
            ld = PoolMgr.getConnection(PoolMgr.ConnType.ADMIN);
            LDAPModificationSet mods = new LDAPModificationSet();
            LDAPAttribute occupant = new LDAPAttribute(ROLE_OCCUPANT, userDn);
            mods.add(LDAPModification.DELETE, occupant);
            DaoUtil.modify(ld, dn, mods, entity);
        }
        catch (LDAPException e)
        {
            String error = CLS_NM + ".deassign role name [" + entity.getName() + "] user dn [" + userDn + "] caught LDAPException=" + e.getLDAPResultCode() + " msg=" + e.getMessage();
            throw new UpdateException(GlobalErrIds.ARLE_USER_DEASSIGN_FAILED, error, e);
        }
        finally
        {
            PoolMgr.closeConnection(ld, PoolMgr.ConnType.ADMIN);
        }
        return entity;
    }


    /**
     * This method will completely remove the AdminRole from the directory.  It will use {@link AdminRole#name} as key.
     * This operation is performed on the {@link GlobalIds#ADMIN_ROLE_ROOT} container.
     *
     * @param role record contains {@link AdminRole#name}.
     * @throws RemoveException in the event LDAP errors occur.
     */
    final void remove(AdminRole role)
        throws RemoveException
    {
        LDAPConnection ld = null;
        String dn = getDn(role);
        try
        {
            ld = PoolMgr.getConnection(PoolMgr.ConnType.ADMIN);
            DaoUtil.delete(ld, dn, role);
        }
        catch (LDAPException e)
        {
            String error = CLS_NM + ".remove role name=" + role.getName() + " LDAPException=" + e.getLDAPResultCode() + " msg=" + e.getMessage();
            throw new RemoveException(GlobalErrIds.ARLE_DELETE_FAILED, error, e);
        }
        finally
        {
            PoolMgr.closeConnection(ld, PoolMgr.ConnType.ADMIN);
        }
    }


    /**
     * This method will retrieve the AdminRole from {@link GlobalIds#ADMIN_ROLE_ROOT} container by name.
     *
     * @param adminRole maps to {@link AdminRole#name}.
     * @return AdminRole back to client.
     * @throws FinderException in the event LDAP errors occur.
     */
    final AdminRole getRole(AdminRole adminRole)
        throws FinderException
    {
        AdminRole entity = null;
        LDAPConnection ld = null;
        String dn = getDn(adminRole);
        try
        {
            ld = PoolMgr.getConnection(PoolMgr.ConnType.ADMIN);
            LDAPEntry findEntry = DaoUtil.read(ld, dn, ROLE_ATRS);
            entity = unloadLdapEntry(findEntry, 0, adminRole.getContextId());
            if (entity == null)
            {
                String warning = CLS_NM + ".getRole name [" + adminRole.getName() + "] no entry found dn [" + dn + "]";
                throw new FinderException(GlobalErrIds.ARLE_NOT_FOUND, warning);
            }
        }
        catch (LDAPException e)
        {
            if (e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT)
            {
                String warning = CLS_NM + ".getRole name [" + adminRole.getName() + "] Obj COULD NOT FIND ENTRY for dn [" + dn + "]";
                throw new FinderException(GlobalErrIds.ARLE_NOT_FOUND, warning);
            }
            String error = CLS_NM + ".getRole dn [" + dn + "] LEXCD=" + e.getLDAPResultCode() + " LEXMSG=" + e;
            throw new FinderException(GlobalErrIds.ARLE_READ_FAILED, error, e);
        }
        finally
        {
            PoolMgr.closeConnection(ld, PoolMgr.ConnType.ADMIN);
        }
        return entity;
    }


    /**
     * @param adminRole
     * @return
     * @throws FinderException
     *
     */
    final List<AdminRole> findRoles(AdminRole adminRole)
        throws FinderException
    {
        List<AdminRole> roleList = new ArrayList<AdminRole>();
        LDAPConnection ld = null;
        LDAPSearchResults searchResults;
        String roleRoot = DaoUtil.getRootDn(adminRole.getContextId(), GlobalIds.ADMIN_ROLE_ROOT);
        String filter;
        try
        {
            String searchVal = VUtil.encodeSafeText(adminRole.getName(), GlobalIds.ROLE_LEN);
            ld = PoolMgr.getConnection(PoolMgr.ConnType.ADMIN);
            filter = "(&(objectclass=" + GlobalIds.ROLE_OBJECT_CLASS_NM + ")("
                + ROLE_NM + "=" + searchVal + "*))";
            searchResults = DaoUtil.search(ld, roleRoot,
                LDAPConnection.SCOPE_ONE, filter, ROLE_ATRS, false, GlobalIds.BATCH_SIZE);
            long sequence = 0;
            while (searchResults.hasMoreElements())
            {
                roleList.add(unloadLdapEntry(searchResults.next(), sequence++, adminRole.getContextId()));
            }
        }
        catch (LDAPException e)
        {
            String error = CLS_NM + ".findRoles name [" + adminRole.getName() + "] caught LDAPException=" + e.getLDAPResultCode() + " msg=" + e.getMessage();
            throw new FinderException(GlobalErrIds.ARLE_SEARCH_FAILED, error, e);
        }
        finally
        {
            PoolMgr.closeConnection(ld, PoolMgr.ConnType.ADMIN);
        }
        return roleList;
    }


    /**
     * @param adminRole
     * @param limit
     * @return
     * @throws FinderException
     *
     */
    final List<String> findRoles(AdminRole adminRole, int limit)
        throws FinderException
    {
        List<String> roleList = new ArrayList<String>();
        LDAPConnection ld = null;
        LDAPSearchResults searchResults;
        String roleRoot = DaoUtil.getRootDn(adminRole.getContextId(), GlobalIds.ADMIN_ROLE_ROOT);
        String filter;
        String searchVal = null;
        try
        {
            searchVal = VUtil.encodeSafeText(adminRole.getName(), GlobalIds.ROLE_LEN);
            ld = PoolMgr.getConnection(PoolMgr.ConnType.ADMIN);
            filter = "(&(objectclass=" + GlobalIds.ROLE_OBJECT_CLASS_NM + ")("
                + ROLE_NM + "=" + searchVal + "*))";
            searchResults = DaoUtil.search(ld, roleRoot,
                LDAPConnection.SCOPE_ONE, filter, ROLE_NM_ATR, false, GlobalIds.BATCH_SIZE, limit);
            while (searchResults.hasMoreElements())
            {
                LDAPEntry entry = searchResults.next();
                roleList.add(DaoUtil.getAttribute(entry, ROLE_NM));
            }
        }
        catch (LDAPException e)
        {
            String error = CLS_NM + ".findRoles name [" + searchVal + "] caught LDAPException=" + e.getLDAPResultCode() + " msg=" + e.getMessage();
            throw new FinderException(GlobalErrIds.ARLE_SEARCH_FAILED, error, e);
        }
        finally
        {
            PoolMgr.closeConnection(ld, PoolMgr.ConnType.ADMIN);
        }
        return roleList;
    }


    /**
     * @param userDn
     * @return
     * @throws FinderException
     */
    final List<String> findAssignedRoles(String userDn, String contextId)
        throws FinderException
    {
        List<String> roleNameList = new ArrayList<String>();
        LDAPConnection ld = null;
        LDAPSearchResults searchResults;
        String roleRoot = DaoUtil.getRootDn(contextId, GlobalIds.ADMIN_ROLE_ROOT);
        try
        {
            ld = PoolMgr.getConnection(PoolMgr.ConnType.ADMIN);
            String filter = "(&(objectclass=" + GlobalIds.ROLE_OBJECT_CLASS_NM + ")";
            filter += "(" + ROLE_OCCUPANT + "=" + userDn + "))";
            searchResults = DaoUtil.search(ld, roleRoot,
                LDAPConnection.SCOPE_ONE, filter, ROLE_NM_ATR, false, GlobalIds.BATCH_SIZE);
            while (searchResults.hasMoreElements())
            {
                roleNameList.add(DaoUtil.getAttribute(searchResults.next(), ROLE_NM));
            }
        }
        catch (LDAPException e)
        {
            String error = CLS_NM + ".findAssignedRoles userDn [" + userDn + "] caught LDAPException=" + e.getLDAPResultCode() + " msg=" + e.getMessage();
            throw new FinderException(GlobalErrIds.ARLE_OCCUPANT_SEARCH_FAILED, error, e);
        }
        finally
        {
            PoolMgr.closeConnection(ld, PoolMgr.ConnType.ADMIN);
        }
        return roleNameList;
    }


    /**
     * @param le
     * @return
     * @throws LDAPException
     */
    private AdminRole unloadLdapEntry(LDAPEntry le, long sequence, String contextId)
        throws LDAPException
    {
        AdminRole entity = new ObjectFactory().createAdminRole();
        entity.setSequenceId(sequence);
        entity.setId(DaoUtil.getAttribute(le, GlobalIds.FT_IID));
        entity.setName(DaoUtil.getAttribute(le, ROLE_NM));
        entity.setDescription(DaoUtil.getAttribute(le, GlobalIds.DESC));
        entity.setOccupants(DaoUtil.getAttributes(le, ROLE_OCCUPANT));
        entity.setOsP(DaoUtil.getAttributeSet(le, ROLE_OSP));
        entity.setOsU(DaoUtil.getAttributeSet(le, ROLE_OSU));
        DaoUtil.unloadTemporal(le, entity);
        entity.setRoleRangeRaw(DaoUtil.getAttribute(le, ROLE_RANGE));
        entity.setParents(AdminRoleUtil.getParents(entity.getName().toUpperCase(), contextId));
        entity.setChildren(AdminRoleUtil.getChildren(entity.getName().toUpperCase(), contextId));
        return entity;
    }

    private String getDn(AdminRole adminRole)
    {
        return GlobalIds.CN + "=" + adminRole.getName() + "," + DaoUtil.getRootDn(adminRole.getContextId(), GlobalIds.ADMIN_ROLE_ROOT);
    }

}
