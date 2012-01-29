//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.01.02 at 09:55:13 AM CST 
//

package com.jts.fortress;

import com.jts.fortress.arbac.AdminRoleRelationship;
import com.jts.fortress.arbac.OrgUnitRelationship;
import com.jts.fortress.arbac.SessionAdminRole;
import com.jts.fortress.arbac.SessionRolePerm;
import com.jts.fortress.arbac.SessionUserRole;
import com.jts.fortress.audit.AuthZ;
import com.jts.fortress.audit.Bind;
import com.jts.fortress.audit.Mod;
import com.jts.fortress.audit.UserAudit;
import com.jts.fortress.pwpolicy.PswdPolicy;
import com.jts.fortress.rbac.PermGrant;
import com.jts.fortress.arbac.AdminRole;
import com.jts.fortress.arbac.OrgUnit;
import com.jts.fortress.arbac.UserAdminRole;
import com.jts.fortress.rbac.PermObj;
import com.jts.fortress.rbac.Permission;
import com.jts.fortress.rbac.Role;
import com.jts.fortress.rbac.RoleRelationship;
import com.jts.fortress.rbac.SDSet;
import com.jts.fortress.rbac.Session;
import com.jts.fortress.rbac.SessionPerm;
import com.jts.fortress.rbac.SessionRole;
import com.jts.fortress.rbac.User;
import com.jts.fortress.rbac.UserRole;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.jts.fortress.model2 package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory
{

    //private final static QName _FortEntity_QNAME = new QName("", "fortEntity");
    private final static QName _FortUser_QNAME = new QName("", "fortUser");
    private final static QName _FortSession_QNAME = new QName("", "fortSession");
    private final static QName _FortRole_QNAME = new QName("", "fortRole");
    private final static QName _FortGrant_QNAME = new QName("", "fortGrant");
    private final static QName _FortOrgUnit_QNAME = new QName("", "fortOrgUnit");
    private final static QName _FortEntity_QNAME = new QName("", "fortEntity");
    private final static QName _FortAdminRole_QNAME = new QName("", "fortAdminRole");
    private final static QName _FortUserRole_QNAME = new QName("", "fortUserRole");
    private final static QName _FortObject_QNAME = new QName("", "fortObject");
    private final static QName _FortPermission_QNAME = new QName("", "fortPermission");
    private final static QName _FortRoleRelationship_QNAME = new QName("", "fortRoleRelationship");
    private final static QName _FortSet_QNAME = new QName("", "fortSet");
    private final static QName _FortPolicy_QNAME = new QName("", "fortPolicy");
    private final static QName _FortUserAdminRole_QNAME = new QName("", "fortUserAdminRole");
    private final static QName _FortAdminRoleRelationship_QNAME = new QName("", "fortAdminRoleRelationship");
    private final static QName _FortOrgUnitRelationship_QNAME = new QName("", "fortOrgUnitRelationship");
    private final static QName _FortBind_QNAME = new QName("", "fortBind");
    private final static QName _FortUserAudit_QNAME = new QName("", "fortUserAudit");
    private final static QName _FortAuthZ_QNAME = new QName("", "fortAuthZ");
    private final static QName _FortMod_QNAME = new QName("", "fortMod");
    private final static QName _FortSessionPerm_QNAME = new QName("", "fortSessionPerm");
    private final static QName _FortSessionRole_QNAME = new QName("", "fortSessionRole");
    private final static QName _FortSessionAdminRole_QNAME = new QName("", "fortSessionAdminRole");
    private final static QName _FortSessionUserRole_QNAME = new QName("", "fortSessionUserRole");
    private final static QName _FortSessionRolePerm_QNAME = new QName("", "fortSessionRolePerm");

    @XmlElementDecl(namespace = "", name = "fortEntity")
    public JAXBElement<FortEntity> createFortEntity(FortEntity value)
    {
        return new JAXBElement<FortEntity>(_FortEntity_QNAME, FortEntity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.rbac.SDSet }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortSet")
    public JAXBElement<SDSet> createFortSet(SDSet value)
    {
        return new JAXBElement<SDSet>(_FortSet_QNAME, SDSet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PswdPolicy }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortPolicy")
    public JAXBElement<PswdPolicy> createFortPolicy(PswdPolicy value)
    {
        return new JAXBElement<PswdPolicy>(_FortPolicy_QNAME, PswdPolicy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.rbac.Session }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortSession")
    public JAXBElement<Session> createFortSession(Session value)
    {
        return new JAXBElement<Session>(_FortSession_QNAME, Session.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.rbac.User }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortUser")
    public JAXBElement<User> createFortUser(User value)
    {
        return new JAXBElement<User>(_FortUser_QNAME, User.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.rbac.UserRole }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortUserRole")
    public JAXBElement<UserRole> createFortUserRole(UserRole value)
    {
        return new JAXBElement<UserRole>(_FortUserRole_QNAME, UserRole.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "fortOrgUnit")
    public JAXBElement<OrgUnit> createFortOrgUnit(OrgUnit value)
    {
        return new JAXBElement<OrgUnit>(_FortOrgUnit_QNAME, OrgUnit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.rbac.Role }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortRole")
    public JAXBElement<Role> createFortRole(Role value)
    {
        return new JAXBElement<Role>(_FortRole_QNAME, Role.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.rbac.Role }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortGrant")
    public JAXBElement<PermGrant> createFortGrant(PermGrant value)
    {
        return new JAXBElement<PermGrant>(_FortGrant_QNAME, PermGrant.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.arbac.OrgUnitRelationship}{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortAdminRoleRelationship")
    public JAXBElement<AdminRoleRelationship> createFortAdminRoleRelationship(AdminRoleRelationship value)
    {
        return new JAXBElement<AdminRoleRelationship>(_FortAdminRoleRelationship_QNAME, AdminRoleRelationship.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.arbac.OrgUnitRelationship}{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortOrgUnitRelationship")
    public JAXBElement<OrgUnitRelationship> createFortOrgUnitRelationship(OrgUnitRelationship value)
    {
        return new JAXBElement<OrgUnitRelationship>(_FortOrgUnitRelationship_QNAME, OrgUnitRelationship.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.rbac.RoleRelationship}{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortRoleRelationship")
    public JAXBElement<RoleRelationship> createFortRoleRelationship(RoleRelationship value)
    {
        return new JAXBElement<RoleRelationship>(_FortRoleRelationship_QNAME, RoleRelationship.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.rbac.Role }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortAdminRole")
    public JAXBElement<AdminRole> createFortAdminRole(AdminRole value)
    {
        return new JAXBElement<AdminRole>(_FortAdminRole_QNAME, AdminRole.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.arbac.UserAdminRole }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortUserAdminRole")
    public JAXBElement<UserAdminRole> createFortUserRole(UserAdminRole value)
    {
        return new JAXBElement<UserAdminRole>(_FortUserAdminRole_QNAME, UserAdminRole.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermObj }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortObject")
    public JAXBElement<PermObj> createFortObject(PermObj value)
    {
        return new JAXBElement<PermObj>(_FortObject_QNAME, PermObj.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.jts.fortress.rbac.Permission }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "fortPermission")
    public JAXBElement<Permission> createFortPermission(Permission value)
    {
        return new JAXBElement<Permission>(_FortObject_QNAME, Permission.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "fortBind")
    public JAXBElement<Bind> createFortEntity(Bind value)
    {
        return new JAXBElement<Bind>(_FortBind_QNAME, Bind.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "fortUserAudit")
    public JAXBElement<UserAudit> createFortUserAudit(UserAudit value)
    {
        return new JAXBElement<UserAudit>(_FortUserAudit_QNAME, UserAudit.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "fortAuthZ")
    public JAXBElement<AuthZ> createFortAuthZ(AuthZ value)
    {
        return new JAXBElement<AuthZ>(_FortAuthZ_QNAME, AuthZ.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "fortMod")
    public JAXBElement<Mod> createFortMod(Mod value)
    {
        return new JAXBElement<Mod>(_FortMod_QNAME, Mod.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "fortSessionPerm")
    public JAXBElement<SessionPerm> createFortSessionPerm(SessionPerm value)
    {
        return new JAXBElement<SessionPerm>(_FortSessionPerm_QNAME, SessionPerm.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "fortSessionRole")
    public JAXBElement<SessionRole> createFortSessionPerm(SessionRole value)
    {
        return new JAXBElement<SessionRole>(_FortSessionRole_QNAME, SessionRole.class, null, value);
    }


    @XmlElementDecl(namespace = "", name = "fortSessionAdminRole")
    public JAXBElement<SessionAdminRole> createFortSessionAdminRole(SessionAdminRole value)
    {
        return new JAXBElement<SessionAdminRole>(_FortSessionAdminRole_QNAME, SessionAdminRole.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "fortSessionUserRole")
    public JAXBElement<SessionUserRole> createFortSessionUserRole(SessionUserRole value)
    {
        return new JAXBElement<SessionUserRole>(_FortSessionUserRole_QNAME, SessionUserRole.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "fortSessionRolePerm")
    public JAXBElement<SessionRolePerm> createFortSessionRolePerm(SessionRolePerm value)
    {
        return new JAXBElement<SessionRolePerm>(_FortSessionRolePerm_QNAME, SessionRolePerm.class, null, value);
    }

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.jts.fortress.model2
     */
    public ObjectFactory()
    {
    }

    /**
     * Create an instance of {@link User }
     */
    public User createUser()
    {
        return new User();
    }

    /**
     * Create an instance of {@link PswdPolicy }
     */
    public PswdPolicy createPswdPolicy()
    {
        return new PswdPolicy();
    }

    /**
     * Create an instance of {@link Session }
     */
    public Session createSession()
    {
        return new Session();
    }

    /**
     * Create an instance of {@link SDSet }
     */
    public SDSet createSDset()
    {
        return new SDSet();
    }

    /**
     * Create an instance of {@link Role }
     */
    public Role createRole()
    {
        return new Role();
    }

    /**
     * Create an instance of {@link PermGrant }
     */
    public PermGrant createPermGrant()
    {
        return new PermGrant();
    }

    /**
     * Create an instance of {@link RoleRelationship }
     */
    public RoleRelationship createRoleRelationship()
    {
        return new RoleRelationship();
    }

    /**
     * Create an instance of {@link AdminRoleRelationship }
     */
    public AdminRoleRelationship createAdminRoleRelationship()
    {
        return new AdminRoleRelationship();
    }

    /**
     * Create an instance of {@link OrgUnitRelationship }
     */
    public OrgUnitRelationship createOrgUnitRelationship()
    {
        return new OrgUnitRelationship();
    }

    /**
     * Create an instance of {@link PermObj }
     */
    public PermObj createPermObj()
    {
        return new PermObj();
    }

    /**
     * Create an instance of {@link Permission }
     */
    public Permission createPermission()
    {
        return new Permission();
    }

    /**
     * Create an instance of {@link Role }
     */
    public AdminRole createAdminRole()
    {
        return new AdminRole();
    }

    /**
     * Create an instance of {@link UserRole }
     */
    public UserRole createUserRole()
    {
        return new UserRole();
    }

    /**
     * Create an instance of {@link OrgUnit }
     */
    public OrgUnit createOrgUnit()
    {
        return new OrgUnit();
    }

    /**
     * Create an instance of {@link UserAdminRole }
     */
    public UserAdminRole createUserAdminRole()
    {
        return new UserAdminRole();
    }

    public UserAudit createUserAudit()
    {
        return new UserAudit();
    }

    public Bind createBind()
    {
        return new Bind();
    }

    public AuthZ createAuthZ()
    {
        return new AuthZ();
    }

    public Mod createMod()
    {
        return new Mod();
    }

    public SessionPerm createSessionPerm()
    {
        return new SessionPerm();
    }

    public SessionRole createSessionRole()
    {
        return new SessionRole();
    }

    public SessionAdminRole createSessionAdminRole()
    {
        return new SessionAdminRole();
    }

    public SessionUserRole createSessionUserRole()
    {
        return new SessionUserRole();
    }

    public SessionRolePerm createSessionRolePerm()
    {
        return new SessionRolePerm();
    }
}
