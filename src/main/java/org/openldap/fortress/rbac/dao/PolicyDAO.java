/*
 * This work is part of OpenLDAP Software <http://www.openldap.org/>.
 *
 * Copyright 1998-2014 The OpenLDAP Foundation.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted only as authorized by the OpenLDAP
 * Public License.
 *
 * A copy of this license is available in the file LICENSE in the
 * top-level directory of the distribution or, alternatively, at
 * <http://www.OpenLDAP.org/license.html>.
 */
package org.openldap.fortress.rbac.dao;


import java.util.List;
import java.util.Set;

import org.openldap.fortress.CreateException;
import org.openldap.fortress.FinderException;
import org.openldap.fortress.RemoveException;
import org.openldap.fortress.UpdateException;
import org.openldap.fortress.rbac.PwPolicy;


/**
 * This DAO class maintains the OpenLDAP Password Policy entity which is a composite of the following structural and aux object classes:
 * <h4>1. organizationalRole Structural Object Class is used to store basic attributes like cn and description</h4>
 * <ul>
 * <li>  ------------------------------------------
 * <li> <code> objectclass ( 2.5.6.14 NAME 'device'</code>
 * <li> <code>DESC 'RFC2256: a device'</code>
 * <li> <code>SUP top STRUCTURAL</code>
 * <li> <code>MUST cn</code>
 * <li> <code>MAY ( serialNumber $ seeAlso $ owner $ ou $ o $ l $ description ) )</code>
 * <li>  ------------------------------------------
 * </ul>
 * <h4>2. pwdPolicy AUXILIARY Object Class is used to store OpenLDAP Password Policies</h4>
 * <ul>
 * <li>  ------------------------------------------
 * <li> <code>objectclass ( 1.3.6.1.4.1.42.2.27.8.2.1</code>
 * <li> <code>NAME 'pwdPolicy'</code>
 * <li> <code>SUP top</code>
 * <li> <code>AUXILIARY</code>
 * <li> <code>MUST ( pwdAttribute )</code>
 * <li> <code>MAY ( pwdMinAge $ pwdMaxAge $ pwdInHistory $ pwdCheckQuality $</code>
 * <li> <code>pwdMinLength $ pwdExpireWarning $ pwdGraceAuthNLimit $ pwdLockout $</code>
 * <li> <code>pwdLockoutDuration $ pwdMaxFailure $ pwdFailureCountInterval $</code>
 * <li> <code>pwdMustChange $ pwdAllowUserChange $ pwdSafeModify ) )</code>
 * <li> <code></code>
 * <li> <code></code>
 * <li>  ------------------------------------------
 * </ul>
 * <h4>3. ftMods AUXILIARY Object Class is used to store Fortress audit variables on target entity</h4>
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
 * This class is thread safe.
 *
 * @author Shawn McKinney
 */
public interface PolicyDAO
{
    /**
     * @param entity
     * @return
     * @throws org.openldap.fortress.CreateException
     *
     */
    PwPolicy create( PwPolicy entity ) throws CreateException;


    /**
     * @param entity
     * @throws org.openldap.fortress.UpdateException
     *
     */
    void update( PwPolicy entity ) throws UpdateException;


    /**
     * @param entity
     * @throws org.openldap.fortress.RemoveException
     */
    void remove( PwPolicy entity ) throws RemoveException;


    /**
     * @param policy
     * @return
     * @throws org.openldap.fortress.FinderException
     *
     */
    PwPolicy getPolicy( PwPolicy policy ) throws FinderException;


    /**
     * @param policy
     * @return
     * @throws org.openldap.fortress.FinderException
     *
     */
    List<PwPolicy> findPolicy( PwPolicy policy ) throws FinderException;


    /**
     * @return
     * @throws FinderException
     */
    Set<String> getPolicies( String contextId ) throws FinderException;
}