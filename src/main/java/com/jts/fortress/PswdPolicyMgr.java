/*
 * Copyright (c) 2009-2012. Joshua Tree Software, LLC.  All Rights Reserved.
 */

package com.jts.fortress;

import com.jts.fortress.pwpolicy.PswdPolicy;

import java.util.List;


/**
 * This object is used to perform administrative and review functions on the PWPOLICIES and USERS data sets.
 * <p/>
 * <h4>Password Policies</h4>
 * <a href="http://www.openldap.org/">OpenLDAP</a> supports the IETF draft <a href="http://tools.ietf.org/html/draft-behera-ldap-password-policy-10/">Password Policies LDAP directories</a></li>.  Policies may be applied at the user, group or global level.
 * <p/>
 * <img src="../../../images/PasswordPolicy.png">
 * <p/>
 * Password enforcement options include:
 * <ol>
 * <li>A configurable limit on failed authentication attempts.</li>
 * <li>A counter to track the number of failed authentication attempts.</li>
 * <li>A time frame in which the limit of consecutive failed authentication attempts must happen before action is taken.</li>
 * <li>The action to be taken when the limit is reached. The action will either be nothing, or the account will be locked.</li>
 * <li>An amount of time the account is locked (if it is to be locked) This can be indefinite.</li>
 * <li>Password expiration.</li>
 * <li>Expiration warning</li>
 * <li>Grace authentications</li>
 * <li>Password history</li>
 * <li>Password minimum age</li>
 * <li>Password minimum length</li>
 * <li>Password Change after Reset</li>
 * <li>Safe Modification of Password</li>
 * </ol>
 * <p/>
 *
 * @author Shawn McKinney
 * @created October 17, 2009
 */
public interface PswdPolicyMgr extends com.jts.fortress.Authorizable
{
    /**
     * This method will add a new policy entry to the POLICIES data set.  This command is valid
     * if and only if the policy entry is not already present in the POLICIES data set.
     * <h4>required parameters</h4>
     * <ul>
     * <li>{@link PswdPolicy#name} - Maps to name attribute of pwdPolicy object class being added.</li>
     * </ul>
     * <h4>optional parameters</h4>
     * <ul>
     * <li>{@link PswdPolicy#minAge} - This attribute holds the number of seconds that must elapse between
     * modifications to the password.  If this attribute is not present, 0
     * seconds is assumed.</li>
     * <li>{@link PswdPolicy#maxAge} - This attribute holds the number of seconds after which a modified
     * password will expire. If this attribute is not present, or if the value is 0 the password
     * does not expire.  If not 0, the value must be greater than or equal
     * to the value of the pwdMinAge.
     * </li>
     * <li>{@link PswdPolicy#inHistory} - This attribute specifies the maximum number of used passwords stored
     * in the pwdHistory attribute. If this attribute is not present, or if the value is 0, used
     * passwords are not stored in the pwdHistory attribute and thus may be reused.</li>
     * <li>{@link PswdPolicy#minLength} - When quality checking is enabled, this attribute holds the minimum
     * number of characters that must be used in a password.  If this
     * attribute is not present, no minimum password length will be
     * enforced.  If the server is unable to check the length (due to a
     * hashed password or otherwise), the server will, depending on the
     * value of the pwdCheckQuality attribute, either accept the password
     * without checking it ('0' or '1') or refuse it ('2').</li>
     * <li>{@link PswdPolicy#expireWarning} - This attribute specifies the maximum number of seconds before a
     * password is due to expire that expiration warning messages will be
     * returned to an authenticating user.  If this attribute is not present, or if the value is 0 no warnings
     * will be returned.  If not 0, the value must be smaller than the value
     * of the pwdMaxAge attribute.</li>
     * <li>{@link PswdPolicy#graceLoginLimit} - This attribute specifies the number of times an expired password can
     * be used to authenticate.  If this attribute is not present or if the
     * value is 0, authentication will fail. </li>
     * <li>{@link PswdPolicy#lockout} - This attribute indicates, when its value is "TRUE", that the password
     * may not be used to authenticate after a specified number of
     * consecutive failed bind attempts.  The maximum number of consecutive
     * failed bind attempts is specified in pwdMaxFailure.  If this attribute is not present, or if the
     * value is "FALSE", the password may be used to authenticate when the number of failed bind
     * attempts has been reached.</li>
     * <li>{@link PswdPolicy#lockoutDuration} - This attribute holds the number of seconds that the password cannot
     * be used to authenticate due to too many failed bind attempts.  If
     * this attribute is not present, or if the value is 0 the password
     * cannot be used to authenticate until reset by a password
     * administrator.</li>
     * <li>{@link PswdPolicy#maxFailure} - This attribute specifies the number of consecutive failed bind
     * attempts after which the password may not be used to authenticate.
     * If this attribute is not present, or if the value is 0, this policy
     * is not checked, and the value of pwdLockout will be ignored.</li>
     * <li>{@link PswdPolicy#failureCountInterval} - This attribute holds the number of seconds after which the password
     * failures are purged from the failure counter, even though no
     * successful authentication occurred.  If this attribute is not present, or if its value is 0, the failure
     * counter is only reset by a successful authentication.</li>
     * <li>{@link PswdPolicy#mustChange} - This attribute specifies with a value of "TRUE" that users must
     * change their passwords when they first bind to the directory after a
     * password is set or reset by a password administrator.  If this
     * attribute is not present, or if the value is "FALSE", users are not
     * required to change their password upon binding after the password
     * administrator sets or resets the password.  This attribute is not set
     * due to any actions specified by this document, it is typically set by
     * a password administrator after resetting a user's password.</li>
     * <li>{@link PswdPolicy#allowUserChange} - This attribute indicates whether users can change their own
     * passwords, although the change operation is still subject to access
     * control.  If this attribute is not present, a value of "TRUE" is
     * assumed.  This attribute is intended to be used in the absence of an access control mechanism.</li>
     * <li>{@link PswdPolicy#safeModify} - This attribute specifies whether or not the existing password must be
     * sent along with the new password when being changed.  If this
     * attribute is not present, a "FALSE" value is assumed.</li>
     * <li>{@link PswdPolicy#checkQuality} - This attribute indicates how the password quality will be verified
     * while being modified or added.  If this attribute is not present, or
     * if the value is '0', quality checking will not be enforced.  A value
     * of '1' indicates that the server will check the quality, and if the
     * server is unable to check it (due to a hashed password or other
     * reasons) it will be accepted.  A value of '2' indicates that the
     * server will check the quality, and if the server is unable to verify
     * it, it will return an error refusing the password. </li>
     * <li>{@link PswdPolicy#attribute} - This holds the name of the attribute to which the password policy is
     * applied.  For example, the password policy may be applied to the
     * userPassword attribute </li>
     * </ul>
     *
     * @param policy Object must contain {@link PswdPolicy#name} and optionally other attributes.
     * @throws SecurityException In the event of data validation or system error.
     */
    public void add(PswdPolicy policy)
        throws SecurityException;


    /**
     * This method will update an exiting policy entry to the POLICIES data set.  This command is valid
     * if and only if the policy entry is already present in the POLICIES data set.
     * <h4>required parameters</h4>
     * <ul>
     * <li>{@link PswdPolicy#name} - Maps to name attribute of pwdPolicy object class being updated.</li>
     * </ul>
     * <h4>optional parameters</h4>
     * <ul>
     * <li>{@link PswdPolicy#minAge} - This attribute holds the number of seconds that must elapse between
     * modifications to the password.  If this attribute is not present, 0
     * seconds is assumed.</li>
     * <li>{@link PswdPolicy#maxAge} - This attribute holds the number of seconds after which a modified
     * password will expire. If this attribute is not present, or if the value is 0 the password
     * does not expire.  If not 0, the value must be greater than or equal
     * to the value of the pwdMinAge.
     * </li>
     * <li>{@link PswdPolicy#inHistory} - This attribute specifies the maximum number of used passwords stored
     * in the pwdHistory attribute. If this attribute is not present, or if the value is 0, used
     * passwords are not stored in the pwdHistory attribute and thus may be reused.</li>
     * <li>{@link PswdPolicy#minLength} - When quality checking is enabled, this attribute holds the minimum
     * number of characters that must be used in a password.  If this
     * attribute is not present, no minimum password length will be
     * enforced.  If the server is unable to check the length (due to a
     * hashed password or otherwise), the server will, depending on the
     * value of the pwdCheckQuality attribute, either accept the password
     * without checking it ('0' or '1') or refuse it ('2').</li>
     * <li>{@link PswdPolicy#expireWarning} - This attribute specifies the maximum number of seconds before a
     * password is due to expire that expiration warning messages will be
     * returned to an authenticating user.  If this attribute is not present, or if the value is 0 no warnings
     * will be returned.  If not 0, the value must be smaller than the value
     * of the pwdMaxAge attribute.</li>
     * <li>{@link PswdPolicy#graceLoginLimit} - This attribute specifies the number of times an expired password can
     * be used to authenticate.  If this attribute is not present or if the
     * value is 0, authentication will fail. </li>
     * <li>{@link PswdPolicy#lockout} - This attribute indicates, when its value is "TRUE", that the password
     * may not be used to authenticate after a specified number of
     * consecutive failed bind attempts.  The maximum number of consecutive
     * failed bind attempts is specified in pwdMaxFailure.  If this attribute is not present, or if the
     * value is "FALSE", the password may be used to authenticate when the number of failed bind
     * attempts has been reached.</li>
     * <li>{@link PswdPolicy#lockoutDuration} - This attribute holds the number of seconds that the password cannot
     * be used to authenticate due to too many failed bind attempts.  If
     * this attribute is not present, or if the value is 0 the password
     * cannot be used to authenticate until reset by a password
     * administrator.</li>
     * <li>{@link PswdPolicy#maxFailure} - This attribute specifies the number of consecutive failed bind
     * attempts after which the password may not be used to authenticate.
     * If this attribute is not present, or if the value is 0, this policy
     * is not checked, and the value of pwdLockout will be ignored.</li>
     * <li>{@link PswdPolicy#failureCountInterval} - This attribute holds the number of seconds after which the password
     * failures are purged from the failure counter, even though no
     * successful authentication occurred.  If this attribute is not present, or if its value is 0, the failure
     * counter is only reset by a successful authentication.</li>
     * <li>{@link PswdPolicy#mustChange} - This attribute specifies with a value of "TRUE" that users must
     * change their passwords when they first bind to the directory after a
     * password is set or reset by a password administrator.  If this
     * attribute is not present, or if the value is "FALSE", users are not
     * required to change their password upon binding after the password
     * administrator sets or resets the password.  This attribute is not set
     * due to any actions specified by this document, it is typically set by
     * a password administrator after resetting a user's password.</li>
     * <li>{@link PswdPolicy#allowUserChange} - This attribute indicates whether users can change their own
     * passwords, although the change operation is still subject to access
     * control.  If this attribute is not present, a value of "TRUE" is
     * assumed.  This attribute is intended to be used in the absence of an access control mechanism.</li>
     * <li>{@link PswdPolicy#safeModify} - This attribute specifies whether or not the existing password must be
     * sent along with the new password when being changed.  If this
     * attribute is not present, a "FALSE" value is assumed.</li>
     * <li>{@link PswdPolicy#checkQuality} - This attribute indicates how the password quality will be verified
     * while being modified or added.  If this attribute is not present, or
     * if the value is '0', quality checking will not be enforced.  A value
     * of '1' indicates that the server will check the quality, and if the
     * server is unable to check it (due to a hashed password or other
     * reasons) it will be accepted.  A value of '2' indicates that the
     * server will check the quality, and if the server is unable to verify
     * it, it will return an error refusing the password. </li>
     * <li>{@link PswdPolicy#attribute} - This holds the name of the attribute to which the password policy is
     * applied.  For example, the password policy may be applied to the
     * userPassword attribute </li>
     * </ul>
     *
     * @param policy Object must contain {@link PswdPolicy#name} and optionally all non-null attributes will be updated.  null attributes will be ignored.
     * @throws SecurityException In the event policy not found , data validation or system error.
     */
    public void update(PswdPolicy policy)
        throws SecurityException;


    /**
     * This method will delete exiting policy entry from the POLICIES data set.  This command is valid
     * if and only if the policy entry is already present in the POLICIES data set.  Existing users that
     * are assigned this policy will be removed from association.
     * <h4>required parameters</h4>
     * <ul>
     * <li>{@link PswdPolicy#name} - Maps to name attribute of pwdPolicy object class being removed.</li>
     * </ul>
     *
     * @param policy Object must contain {@link PswdPolicy#name} of the policy entity to remove.
     * @throws SecurityException In the event policy entity not found or system error.
     */
    public void delete(PswdPolicy policy)
        throws SecurityException;


    /**
     * This method will return the password policy entity to the caller.  This command is valid
     * if and only if the policy entry is present in the POLICIES data set.
     * <h4>required parameters</h4>
     * <ul>
     * <li>{@link PswdPolicy#name} - Maps to name attribute of pwdPolicy object class being read.</li>
     * </ul>
     *
     * @param name String contains the {@link PswdPolicy#name} of the policy entity to read.
     * @return PswdPolicy entity returns fully populated with attributes.
     * @throws SecurityException In the event policy entry not found, data validation or system error.
     */
    public com.jts.fortress.pwpolicy.PswdPolicy read(String name)
        throws SecurityException;


    /**
     * This method will return a list of all password policy entities that match a particular search string.
     * This command will return an empty list of no matching entries are found.
     *
     * @param searchVal String contains the leading chars of a policy entity.  This search is not case sensitive.
     * @return List<PswdPolicy> contains all matching password policy entities. If no records found this will be empty.
     * @throws SecurityException In the event of data validation or system error.
     */
    public List<PswdPolicy> search(String searchVal)
        throws SecurityException;


    /**
     * This method will associate a user entity with a password policy entity.  This function is valid
     * if and only if the user is a member of the USERS data set and the policyName refers to a
     * policy that is a member of the PWPOLICIES data set.
     *
     * @param userId     Contains {@link com.jts.fortress.rbac.User#userId} of a User entity in USERS data set.
     * @param policyName String contains the {@link PswdPolicy#name} of a pw policy entity contained within the PWPOLICIES data set.
     * @throws SecurityException thrown in the event either user or policy not valid or system error.
     */
    public void updateUserPolicy(String userId, String policyName)
        throws SecurityException;


    /**
     * This method will remove the pw policy assignment from a user entity.  This function is valid
     * if and only if the user is a member of the USERS data set and the policy attribute is assigned.
     * Removal of pw policy assignment will revert the user's policy to use the global default for OpenLDAP
     * instance that contains user.
     *
     * @param userId Contains {@link com.jts.fortress.rbac.User#userId} of a User entity in USERS data set.
     * @throws SecurityException Thrown in the event either user not valid or system error.
     */
    public void deletePasswordPolicy(String userId)
        throws SecurityException;
}
