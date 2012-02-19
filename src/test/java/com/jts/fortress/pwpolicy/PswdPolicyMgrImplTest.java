/*
 * Copyright (c) 2009-2012. Joshua Tree Software, LLC.  All Rights Reserved.
 */

package com.jts.fortress.pwpolicy;

import com.jts.fortress.AccessMgrFactory;
import com.jts.fortress.SecurityException;
import com.jts.fortress.AccessMgr;
import com.jts.fortress.AdminMgr;
import com.jts.fortress.PswdPolicyMgr;
import com.jts.fortress.PswdPolicyMgrFactory;
import com.jts.fortress.rbac.AdminMgrImplTest;
import com.jts.fortress.rbac.FortressJUnitTest;
import com.jts.fortress.rbac.Session;
import com.jts.fortress.rbac.TestUtils;
import com.jts.fortress.rbac.User;
import com.jts.fortress.rbac.UserTestData;
import com.jts.fortress.arbac.DelegatedMgrImplTest;
import com.jts.fortress.constants.GlobalErrIds;
import com.jts.fortress.util.LogUtil;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.util.List;

/**
 * PswdPolicyMgrImpl Tester.
 *
 * @author smckinn
 * @version 1.0
 * @since <pre>02/20/2010</pre>
 */
public class PswdPolicyMgrImplTest extends TestCase
{
    private static final String CLS_NM = PswdPolicyMgrImplTest.class.getName();
    final protected static Logger log = Logger.getLogger(CLS_NM);

    public PswdPolicyMgrImplTest(String name)
    {
        super(name);
    }

    static Session adminSess = null;

    public void setUp() throws Exception
    {
        super.setUp();
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
    }

    public static Test suiteX()
    {
        TestSuite suite = new TestSuite();
        suite.addTest(new PswdPolicyMgrImplTest("testDelete"));
        suite.addTest(new AdminMgrImplTest("testDeleteUser"));
        suite.addTest(new PswdPolicyMgrImplTest("testAdd"));
        suite.addTest(new AdminMgrImplTest("testAddUser"));
        suite.addTest(new PswdPolicyMgrImplTest("testMinAge"));
        suite.addTest(new PswdPolicyMgrImplTest("testMaxAge"));
        suite.addTest(new PswdPolicyMgrImplTest("testInHistory"));
        suite.addTest(new PswdPolicyMgrImplTest("testMinLength"));
        suite.addTest(new PswdPolicyMgrImplTest("testExpireWarning"));
        suite.addTest(new PswdPolicyMgrImplTest("testGraceLoginLimit"));
        suite.addTest(new PswdPolicyMgrImplTest("testMaxFailure"));
        suite.addTest(new PswdPolicyMgrImplTest("testLockoutDuration"));
        suite.addTest(new PswdPolicyMgrImplTest("testLockout"));
        suite.addTest(new PswdPolicyMgrImplTest("testFailureCountInterval"));
        suite.addTest(new PswdPolicyMgrImplTest("testMustChange"));
        suite.addTest(new PswdPolicyMgrImplTest("testAllowUserChange"));
        suite.addTest(new PswdPolicyMgrImplTest("testSafeModify"));        
        return suite;
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        suite.addTest(new PswdPolicyMgrImplTest("testDelete"));
        suite.addTest(new AdminMgrImplTest("testDeleteUser"));
        suite.addTest(new PswdPolicyMgrImplTest("testAdd"));
        suite.addTest(new AdminMgrImplTest("testAddUser"));
        //suite.addTest(new PswdPolicyMgrImplTest("testMinAge"));
        //suite.addTest(new PswdPolicyMgrImplTest("testMaxAge"));
        //suite.addTest(new PswdPolicyMgrImplTest("testInHistory"));
        //suite.addTest(new PswdPolicyMgrImplTest("testMinLength"));
        //suite.addTest(new PswdPolicyMgrImplTest("testExpireWarning"));
        //suite.addTest(new PswdPolicyMgrImplTest("testGraceLoginLimit"));
        //suite.addTest(new PswdPolicyMgrImplTest("testMaxFailure"));        
        //suite.addTest(new PswdPolicyMgrImplTest("testLockoutDuration"));
        //suite.addTest(new PswdPolicyMgrImplTest("testLockout"));
        //suite.addTest(new PswdPolicyMgrImplTest("testFailureCountInterval"));
        //suite.addTest(new PswdPolicyMgrImplTest("testMustChange"));
        //suite.addTest(new PswdPolicyMgrImplTest("testAllowUserChange"));
        suite.addTest(new PswdPolicyMgrImplTest("testSafeModify"));
        return suite;
    }


    public static <T> T[] arrayMerge(T[]... arrays)
    {
        // Determine required size of new array

        int count = 0;
        for (T[] array : arrays)
        {
            count += array.length;
        }

        // create new array of required class

        T[] mergedArray = (T[]) Array.newInstance(
                arrays[0][0].getClass(), count);

        // Merge each array into new array

        int start = 0;
        for (T[] array : arrays)
        {
            System.arraycopy(array, 0,
                             mergedArray, start, array.length);
            start += array.length;
        }
        return (T[]) mergedArray;
    }

    /**
     * @throws SecurityException
     */
    public void testMinAge()
    {
        minAge("PWMIN1 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[0], UserTestData.USERS_TU5_UPD[0], PolicyTestData.POLICIES_TP1[0]);
        minAge("PWMIN2 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[1], UserTestData.USERS_TU5_UPD[1], PolicyTestData.POLICIES_TP1[1]);
        //minAge("PWMIN3 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[2], UserTestData.USERS_TU5_UPD[2], PolicyTestData.POLICIES_TP1[2]);
        //minAge("PWMIN4 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[3], UserTestData.USERS_TU5_UPD[3], PolicyTestData.POLICIES_TP1[3]);
    }


    /**
     * PT1
     * <p/>
     * 5.2.2  pwdMinAge
     * <p/>
     * This attribute holds the number of seconds that must elapse between
     * modifications to the password.  If this attribute is not present, 0
     * seconds is assumed.                                                             "chg"
     *
     * @param msg
     * @param newusr
     * @param oldusr *
     * @param plcy
     */
    public void minAge(String msg, String[] oldusr, String[] newusr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();
            User oldUser = UserTestData.getUser(oldusr);
            oldUser.setPwPolicy(PolicyTestData.getName(plcy));
            adminMgr.updateUser(oldUser);
            TestUtils.sleep(PolicyTestData.getMinAge(plcy));
            adminMgr.changePassword(UserTestData.getUser(oldusr), UserTestData.getPassword(newusr));
            User newUser = UserTestData.getUser(newusr);
            try
            {
                newUser.setPassword("changed".toCharArray());
                adminMgr.changePassword(UserTestData.getUser(newusr), newUser.getPassword());
                fail(CLS_NM + ".minAge name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(oldusr) + "] failed min age test");
            }
            catch (SecurityException ex)
            {
                assertTrue(CLS_NM + ".minAge invalid error message userId [" + UserTestData.getUserId(oldusr) + "]", ex.getErrorId() == GlobalErrIds.PSWD_CONST_VIOLATION);
                // still good
                TestUtils.sleep(PolicyTestData.getMinAge(plcy));
                adminMgr.changePassword(UserTestData.getUser(newusr), newUser.getPassword());
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".minAge caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    public void testMaxAge()
    {
        maxAge("PWMAX3 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[2], UserTestData.USERS_TU5_UPD[2], PolicyTestData.POLICIES_TP1[2]);
        maxAge("PWMAX4 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[3], UserTestData.USERS_TU5_UPD[3], PolicyTestData.POLICIES_TP1[3]);
    }

    /**
     * PT2
     * 5.2.3  pwdMaxAge
     * <p/>
     * This attribute holds the number of seconds after which a modified
     * password will expire.
     * <p/>
     * If this attribute is not present, or if the value is 0 the password
     * does not expire.  If not 0, the value must be greater than or equal
     * to the value of the pwdMinAge.*
     *
     * @param msg
     * @param oldusr
     * @param newusr
     * @param plcy
     */
    public void maxAge(String msg, String[] oldusr, String[] newusr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();
            AccessMgr accessMgr = AccessMgrFactory.createInstance();
            User oldUser = UserTestData.getUser(oldusr);
            User newUser = UserTestData.getUser(newusr);
            oldUser.setPwPolicy(PolicyTestData.getName(plcy));
            adminMgr.updateUser(oldUser);
            char[] newPassword = newUser.getPassword();
            adminMgr.changePassword(oldUser, newPassword);
            Session s1 = null;
            oldUser.setPassword(newPassword);
            for (int i = 0; i < 3; i++)
            {
                TestUtils.sleep(PolicyTestData.getMaxAge(plcy));
                TestUtils.sleep(1);
                try
                {
                    s1 = accessMgr.createSession(oldUser, false);
                    fail(CLS_NM + ".maxAge name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(oldusr) + "] failed age test");
                }
                catch (SecurityException ex)
                {
                    assertTrue(CLS_NM + ".maxAge invalid error message userId [" + UserTestData.getUserId(oldusr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_EXPIRED);
                    // still good
                }
                newPassword = "changedabc".toCharArray();
                oldUser = new User(oldUser.getUserId());
                oldUser.setPassword(newPassword);
                // since this password is now expired we have to call update rather than changePassword:
                adminMgr.updateUser(oldUser);
                s1 = accessMgr.createSession(oldUser, false);
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".maxAge caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }


    public void testInHistory()
    {
        inHistory("PWHIST5 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[4], PolicyTestData.POLICIES_TP1[4]);
        inHistory("PWHIST6 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[5], PolicyTestData.POLICIES_TP1[5]);
    }

    /**
     * PT3
     * 5.2.4  pwdInHistory
     * <p/>
     * This attribute specifies the maximum number of used passwords stored
     * in the pwdHistory attribute.
     * <p/>
     * If this attribute is not present, or if the value is 0, used
     * passwords are not stored in the pwdHistory attribute and thus may be
     * reused.
     *
     * @param msg
     * @param usr
     * @param plcy     
     */
    public void inHistory(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();

            User user = UserTestData.getUser(usr);
            policyMgr.updateUserPolicy(user.getUserId(), PolicyTestData.getName(plcy));
            int numHistory = PolicyTestData.getInHistory(plcy);
            for(int i = 0; i < numHistory + 1; i++)
            {
                String newPassword = UserTestData.getPassword(usr) + Integer.toString(i);
                log.debug("inHistory change pw="  + user.getPassword());
                adminMgr.changePassword(user, newPassword.toCharArray());
                user.setPassword(newPassword.toCharArray());
                try
                {
                    log.debug("inHistory change pw2="  + user.getPassword());
                    adminMgr.changePassword(user, newPassword.toCharArray());
                }
                catch (SecurityException ex)
                {
                    assertTrue(CLS_NM + ".inHistory invalid error message userId [" + user.getUserId() + "]", ex.getErrorId() == GlobalErrIds.PSWD_CONST_VIOLATION);
                    // still good
                }
            }
            try
            {
                // now try to change back to original password, this should pass
                adminMgr.changePassword(user, UserTestData.getPassword(usr));
            }
            catch (SecurityException ex)
            {
                String error = CLS_NM + ".inHistory caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage();
                log.error(error);
                fail(error);
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".inHistory caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }


    public void testMinLength()
    {
        minLength("PWLEN7 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[6], PolicyTestData.POLICIES_TP1[6]);
        minLength("PWLEN8 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[7], PolicyTestData.POLICIES_TP1[7]);
    }

    /**
     * PT4
     * 5.2.6  pwdMinLength
     * <p/>
     * When quality checking is enabled, this attribute holds the minimum
     * number of characters that must be used in a password.  If this
     * attribute is not present, no minimum password length will be
     * enforced.  If the server is unable to check the length (due to a
     * hashed password or otherwise), the server will, depending on the
     * value of the pwdCheckQuality attribute, either accept the password
     * without checking it ('0' or '1') or refuse it ('2').
     *
     * @param msg
     * @param usr
     * @param plcy
     */
    public void minLength(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();

            User user = UserTestData.getUser(usr);
            policyMgr.updateUserPolicy(user.getUserId(), PolicyTestData.getName(plcy));
            try
            {
                int min = PolicyTestData.getMinLength(plcy);
                log.debug("testMinLength min=" + min + " len pw=" + user.getPassword().length);
                String newPassword = new String(user.getPassword()).substring(0, min-1);
                adminMgr.changePassword(user, newPassword.toCharArray());
                fail(CLS_NM + ".minLength name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed length test");
            }
            catch (SecurityException ex)
            {
                assertTrue(CLS_NM + ".minLength invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.PSWD_CONST_VIOLATION);
                // still good
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".minLength caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }


    public void testExpireWarning()
    {
        expireWarning("PWEXP7 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[8], PolicyTestData.POLICIES_TP1[8]);
        expireWarning("PWEXP8 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[9], PolicyTestData.POLICIES_TP1[9]);
    }

    /**
     * PT5
     * 5.2.7  pwdExpireWarning
     * <p/>
     * This attribute specifies the maximum number of seconds before a
     * password is due to expire that expiration warning messages will be
     * returned to an authenticating user.
     * <p/>
     * If this attribute is not present, or if the value is 0 no warnings
     * will be returned.  If not 0, the value must be smaller than the value
     * of the pwdMaxAge attribute.
     *
     * @param msg
     * @param usr
     * @param plcy
     */
    public void expireWarning(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();
            AccessMgr accessMgr = AccessMgrFactory.createInstance();
            User user = UserTestData.getUser(usr);
            long expireSecs = PolicyTestData.getExpireWarning(plcy);
            long maxSecs = PolicyTestData.getMaxAge(plcy);
            long elapsedWait = maxSecs - expireSecs;
            String newPassword = UserTestData.getPassword(usr) + "a";
            user.setPassword(newPassword.toCharArray());
            user.setPwPolicy(PolicyTestData.getName(plcy));
            // because the password max age is so short, need to set new password, otherwise it will have already expired:
            adminMgr.updateUser(user);
            // now do the password change to start the clock ticking:
            newPassword = UserTestData.getPassword(usr) + "b";
            adminMgr.changePassword(user, newPassword.toCharArray());
            user.setPassword(newPassword.toCharArray());
            Session s1 = accessMgr.createSession(user, false);
            assertTrue(CLS_NM + ".expireWarning invalid error message userId [" + UserTestData.getUserId(usr) + "]", s1.getExpirationSeconds() == 0);
            TestUtils.sleep(elapsedWait);
            // add one second for good measure:
            TestUtils.sleep(1);
            s1 = accessMgr.createSession(user, false);
            assertTrue(CLS_NM + ".expireWarning invalid error message 2 userId [" + UserTestData.getUserId(usr) + "]", (0 < s1.getExpirationSeconds()) && (s1.getExpirationSeconds() < maxSecs));
            TestUtils.sleep(elapsedWait);
            try
            {
                s1 = accessMgr.createSession(user, false);
                fail(CLS_NM + ".expireWarning name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed expired pw test");
            }
            catch (SecurityException ex)
            {
                assertTrue(CLS_NM + ".expireWarning invalid error message 3 userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_EXPIRED);
                // still good
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".expireWarning caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }


    public void testGraceLoginLimit()
    {
        graceLoginLimit("PWGRACE9 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[10], PolicyTestData.POLICIES_TP1[10]);
        graceLoginLimit("PWGRACE10 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[11], PolicyTestData.POLICIES_TP1[11]);
    }

    /**
     * PT6
     * 5.2.8  pwdGraceAuthNLimit
     * <p/>
     * This attribute specifies the number of times an expired password can
     * be used to authenticate.  If this attribute is not present or if the
     * value is 0, authentication will fail.
     * @param msg
     * @param usr
     * @param plcy
     */
    public void graceLoginLimit(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();
            AccessMgr accessMgr = AccessMgrFactory.createInstance();
            User user = UserTestData.getUser(usr);
            user.setPwPolicy(PolicyTestData.getName(plcy));
            adminMgr.updateUser(user);
            String newPassword = user.getPassword() + "a";
            adminMgr.changePassword(user, newPassword.toCharArray());
            Session s1 = null;
            user.setPassword(newPassword.toCharArray());
            TestUtils.sleep(PolicyTestData.getMaxAge(plcy));
            TestUtils.sleep(1);                       
            int numGrace = PolicyTestData.getGraceLoginLimit(plcy);
            for (int i = 0; i < numGrace; i++)
            {
                try
                {
                    s1 = accessMgr.createSession(user, false);
                    TestUtils.sleep(1);                    
                }
                catch (SecurityException ex)
                {
                    fail(CLS_NM + ".graceLoginLimit name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed grace allowed=" + numGrace + " iteration=" + i);
                    assertTrue(CLS_NM + ".graceLoginLimit invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_EXPIRED);
                    // still good
                }
            }
            try
            {               
                s1 = accessMgr.createSession(user, false);
                fail(CLS_NM + ".graceLoginLimit name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed grace test 2");
            }
            catch (SecurityException ex)
            {
                assertTrue(CLS_NM + ".graceLoginLimit invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_EXPIRED);
                // still good
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".graceLoginLimit caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }


    public void testMaxFailure()
    {
        maxFailure("PWMAXFAILK11 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[12], PolicyTestData.POLICIES_TP1[12]);
        maxFailure("PWMAXFAIL12 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[13], PolicyTestData.POLICIES_TP1[13]);
    }

    /**
     * PT7
     * 5.2.11  pwdMaxFailure
     * <p/>
     * This attribute specifies the number of consecutive failed bind
     * attempts after which the password may not be used to authenticate.
     * If this attribute is not present, or if the value is 0, this policy
     * is not checked, and the value of pwdLockout will be ignored.
     *
     * @param msg
     * @param usr
     * @param plcy
     */
    public void maxFailure(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();
            AccessMgr accessMgr = AccessMgrFactory.createInstance();
            User user = UserTestData.getUser(usr);
            policyMgr.updateUserPolicy(user.getUserId(), PolicyTestData.getName(plcy));
            Session s1 = null;
            int maxFailures = PolicyTestData.getMaxFailure(plcy);
            for (int i = 0; i < maxFailures; i++)
            {
                try
                {
                    User badUser = new User(user.getUserId(), "wrongpw".toCharArray());
                    s1 = accessMgr.createSession(badUser, false);
                    fail(CLS_NM + ".maxFailure name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed max failure test=" + maxFailures + " iteration=" + i);
                }
                catch (SecurityException ex)
                {
                    assertTrue(CLS_NM + ".maxFailure invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_INVLD);
                    // still good
                    TestUtils.sleep(1);
                }
            }
            try
            {
                // now try with valid password - better be locked out...
                s1 = accessMgr.createSession(user, false);
                fail(CLS_NM + ".maxFailure name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed max failure test 2");
            }
            catch (SecurityException ex)
            {
                assertTrue(CLS_NM + ".maxFailure invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_LOCKED);
                // still good
            }
            adminMgr.unlockUserAccount(user);
            // now try with valid password - better work this time...
            s1 = accessMgr.createSession(user, false);
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".maxFailure caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }



    public void testLockoutDuration() throws com.jts.fortress.SecurityException
    {
        lockoutDuration("PWDURATION13 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[14], PolicyTestData.POLICIES_TP1[14]);
        lockoutDuration("PWDURATION14 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[15], PolicyTestData.POLICIES_TP1[15]);
    }

    /**
     * PT8
     * 5.2.10  pwdLockoutDuration
     * <p/>
     * This attribute holds the number of seconds that the password cannot
     * be used to authenticate due to too many failed bind attempts.  If
     * this attribute is not present, or if the value is 0 the password
     * cannot be used to authenticate until reset by a password
     * administrator.
     *
     * @param msg
     * @param usr
     * @param plcy
     */
    public void lockoutDuration(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            AccessMgr accessMgr = AccessMgrFactory.createInstance();
            User user = UserTestData.getUser(usr);
            policyMgr.updateUserPolicy(user.getUserId(), PolicyTestData.getName(plcy));
            Session s1 = null;
            int maxFailures = PolicyTestData.getMaxFailure(plcy);
            int lockoutDuration = PolicyTestData.getLockoutDuration(plcy);
            for (int i = 0; i < maxFailures; i++)
            {
                try
                {
                    User badUser = new User(user.getUserId(), "wrongpw".toCharArray());
                    s1 = accessMgr.createSession(badUser, false);
                    fail(CLS_NM + ".lockoutDuration name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed lockout duration test=" + maxFailures + " iteration=" + i);
                }
                catch (SecurityException ex)
                {
                    assertTrue(CLS_NM + ".lockoutDuration invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_INVLD);
                    // still good
                    TestUtils.sleep(1);
                }
            }
            try
            {
                // now try with valid password - better be locked out...
                s1 = accessMgr.createSession(user, false);
                fail(CLS_NM + ".lockoutDuration name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed lockout duration test 2");
            }
            catch (SecurityException ex)
            {
                assertTrue(CLS_NM + ".lockoutDuration invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_LOCKED);
                // still good
            }

            // now sleep for lockout duration - password should unlock automatically:
            TestUtils.sleep(lockoutDuration);
            // sleep one more second for good measure.
            TestUtils.sleep(1);
            // now try with valid password - better work this time...
            s1 = accessMgr.createSession(user, false);
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".lockoutDuration caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }


    public void testLockout()
    {
        lockout("PWLOCK15 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[16], PolicyTestData.POLICIES_TP1[16]);
        lockout("PWLOCK16 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[17], PolicyTestData.POLICIES_TP1[17]);
    }

    /**
     * PT9
     * 5.2.9  pwdLockout
     * <p/>
     * This attribute indicates, when its value is "TRUE", that the password
     * may not be used to authenticate after a specified number of
     * consecutive failed bind attempts.  The maximum number of consecutive
     * failed bind attempts is specified in pwdMaxFailure.
     * <p/>
     * If this attribute is not present, or if the value is "FALSE", the
     * password may be used to authenticate when the number of failed bind
     * attempts has been reached.
     *
     * @param msg
     * @param usr
     * @param plcy
     */
    public void lockout(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();
            AccessMgr accessMgr = AccessMgrFactory.createInstance();
            User user = UserTestData.getUser(usr);

            policyMgr.updateUserPolicy(user.getUserId(), PolicyTestData.getName(plcy));
            Session s1 = null;
            for (int i = 0; i < 3; i++)
            {
                // first lock it:
                adminMgr.lockUserAccount(user);
                try
                {
                    // because account is locked, this better fail:
                    s1 = accessMgr.createSession(user, false);
                    fail(CLS_NM + ".lockout name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed lockout test iteration=" + i);
                }
                catch (SecurityException ex)
                {
                    assertTrue(CLS_NM + ".lockout invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_LOCKED);
                    // still good
                    TestUtils.sleep(1);
                }
                // now unlock it:
                adminMgr.unlockUserAccount(user);
                // this better work:
                s1 = accessMgr.createSession(user, false);
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".lockout caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }


    public void testFailureCountInterval()
    {
        failureCountInterval("PWINTERVAL17 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[18], PolicyTestData.POLICIES_TP1[18]);
        failureCountInterval("PWINTERVAL18 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[19], PolicyTestData.POLICIES_TP1[19]);
    }

    /**
     * PT10
     * <p/>
     * This attribute holds the number of seconds after which the password
     * failures are purged from the failure counter, even though no
     * successful authentication occurred.
     * <p/>
     * If this attribute is not present, or if its value is 0, the failure
     * counter is only reset by a successful authentication.
     *
     * @param msg
     * @param usr
     * @param plcy
     */
    public void failureCountInterval(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            AccessMgr accessMgr = AccessMgrFactory.createInstance();
            User user = UserTestData.getUser(usr);

            policyMgr.updateUserPolicy(user.getUserId(), PolicyTestData.getName(plcy));
            Session s1 = null;
            int maxFailures = PolicyTestData.getMaxFailure(plcy);
            int failureInterval = PolicyTestData.getFailureCountInterval(plcy);
            for (int i = 0; i < maxFailures-1; i++)
            {
                try
                {
                    User badUser = new User(user.getUserId(), "wrongpw".toCharArray());
                    s1 = accessMgr.createSession(badUser, false);
                    fail(CLS_NM + ".failureCountInterval name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed failure count interval test, maxfailures=" + maxFailures + " iteration=" + i);
                }
                catch (SecurityException ex)
                {
                    assertTrue(CLS_NM + ".failureCountInterval invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_INVLD);
                    // still good
                    TestUtils.sleep(1);
                }
            }
            // now sleep for failure count interval - password failure count should reset automatically:
            TestUtils.sleep(failureInterval);
            // sleep one more second for good measure.
            TestUtils.sleep(1);
            // now loop thru another set of bad pw tries:
            for (int i = 0; i < maxFailures-1; i++)
            {
                try
                {
                    User badUser = new User(user.getUserId(), "wrongpw".toCharArray());
                    s1 = accessMgr.createSession(badUser, false);
                    fail(CLS_NM + ".failureCountInterval name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed failure count interval test 2, maxfailures=" + maxFailures + " iteration=" + i);
                }
                catch (SecurityException ex)
                {
                    assertTrue(CLS_NM + ".failureCountInterval invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_INVLD);
                    // still good
                    TestUtils.sleep(1);
                }
            }
            // now sleep for failure count interval - password failure count should reset automatically:
            TestUtils.sleep(failureInterval);
            // sleep one more second for good measure.
            TestUtils.sleep(1);

            // now try with valid password - it should work...
            s1 = accessMgr.createSession(user, false);
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".failureCountInterval caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    public void testMustChange()
    {
        mustChange("PWMUST19 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[20], PolicyTestData.POLICIES_TP1[20]);
        mustChange("PWMUST20 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[21], PolicyTestData.POLICIES_TP1[21]);
    }

    /**
     * PT11
     * This attribute specifies with a value of "TRUE" that users must
     * change their passwords when they first bind to the directory after a
     * password is set or reset by a password administrator.  If this
     * attribute is not present, or if the value is "FALSE", users are not
     * required to change their password upon binding after the password
     * administrator sets or resets the password.  This attribute is not set
     * due to any actions specified by this document, it is typically set by
     * a password administrator after resetting a user's password.
     *
     * @param msg
     * @param usr
     * @param plcy
     */
    public void mustChange(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();
            AccessMgr accessMgr = AccessMgrFactory.createInstance();
            User user = UserTestData.getUser(usr);
            policyMgr.updateUserPolicy(user.getUserId(), PolicyTestData.getName(plcy));
            boolean mustChange = PolicyTestData.getMustChange(plcy);
            adminMgr.resetPassword(user, "newpassword".toCharArray());
            Session s1 = null;
            if(mustChange)
            {
                try
                {
                    // because mustchange flag is set, this better fail:
                    User badUser = new User(user.getUserId(), "newpassword".toCharArray());
                    s1 = accessMgr.createSession(badUser, false);
                    fail(CLS_NM + ".mustChange name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed must change test flag=" + mustChange);
                }
                catch (SecurityException ex)
                {
                    assertTrue(CLS_NM + ".mustChange invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_RESET);
                    // still good
                    TestUtils.sleep(1);
                }
            }
            else
            {
                // this better work:
                User goodUser = new User(user.getUserId(), "newpassword".toCharArray());
                s1 = accessMgr.createSession(goodUser, false);
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".mustChange policy [" + PolicyTestData.getName(plcy) + "] caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }


    public void testAllowUserChange()
    {
        allowUserChange("PWALLOW21 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[22], PolicyTestData.POLICIES_TP1[22]);
        allowUserChange("PWALLOW22 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[23], PolicyTestData.POLICIES_TP1[23]);
    }

    /**
     * PT12
     * This attribute indicates whether users can change their own
     * passwords, although the change operation is still subject to access
     * control.  If this attribute is not present, a value of "TRUE" is
     * assumed.  This attribute is intended to be used in the absense of an
     * access control mechanism.
     *
     * @param msg
     * @param usr
     * @param plcy
     */
    public void allowUserChange(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();
            User user = UserTestData.getUser(usr);

            policyMgr.updateUserPolicy(user.getUserId(), PolicyTestData.getName(plcy));
            boolean allowChange = PolicyTestData.getAllowUserChange(plcy);
            if(!allowChange)
            {
                try
                {
                    // because allowchange flag is false, this better fail:
                    adminMgr.changePassword(user, "newPassword".toCharArray());
                    fail(CLS_NM + ".allowUserChange name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed allow change test flag=" + allowChange);
                }
                catch (SecurityException ex)
                {
                    assertTrue(CLS_NM + ".allowUserChange invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_MOD_NOT_ALLOWED);
                    // still good
                    TestUtils.sleep(1);
                }
            }
            else
            {
                // this better work:
                adminMgr.changePassword(user, "newPassword".toCharArray());
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".allowUserChange policy [" + PolicyTestData.getName(plcy) + "] caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }


    public void testSafeModify()
    {
        safeModify("PWSAFEW23 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[24], PolicyTestData.POLICIES_TP1[24]);
        safeModify("PWSAFE24 " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1") + " " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5"), UserTestData.USERS_TU5[25], PolicyTestData.POLICIES_TP1[25]);
    }

    /**
     * PT13
     * 5.2.15  pwdSafeModify
     * <p/>
     * This attribute specifies whether or not the existing password must be
     * sent along with the new password when being changed.  If this
     * attribute is not present, a "FALSE" value is assumed.
     *
     * @param msg
     * @param usr
     * @param plcy
     */
    public void safeModify(String msg, String[] usr, String[] plcy)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            AdminMgr adminMgr = AdminMgrImplTest.getManagedAdminMgr();
            User user = UserTestData.getUser(usr);
            policyMgr.updateUserPolicy(user.getUserId(), PolicyTestData.getName(plcy));
            boolean safeModify = PolicyTestData.getSafeModify(plcy);
            if(safeModify)
            {
                try
                {
                    // because safe modify flag is true, this better fail:
                    adminMgr.changePassword(user, "newPassword".toCharArray());
                    fail(CLS_NM + ".safeModify name [" + PolicyTestData.getName(plcy) + "] user [" + UserTestData.getUserId(usr) + "] failed safe modify test flag=" + safeModify);
                }
                catch (SecurityException ex)
                {
                    assertTrue(CLS_NM + ".safeModify invalid error message userId [" + UserTestData.getUserId(usr) + "]", ex.getErrorId() == GlobalErrIds.USER_PW_MOD_NOT_ALLOWED);
                    // still good
                    TestUtils.sleep(1);
                }
            }
            else
            {
                // this better work:
                adminMgr.changePassword(user, "newPassword".toCharArray());
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".safeModify policy [" + PolicyTestData.getName(plcy) + "] caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }


    /**
     * PT14
     * 5.2.5  pwdCheckQuality
     * <p/>
     * This attribute indicates how the password quality will be verified
     * while being modified or added.  If this attribute is not present, or
     * if the value is '0', quality checking will not be enforced.  A value
     * of '1' indicates that the server will check the quality, and if the
     * server is unable to check it (due to a hashed password or other
     * reasons) it will be accepted.  A value of '2' indicates that the
     * server will check the quality, and if the server is unable to verify
     * it, it will return an error refusing the password.
     *
     * @param msg
     * @param pArray
     */
    public void checkQuality(String msg, String[][] pArray)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            for (String[] plcy : pArray)
            {
                PswdPolicy policy = PolicyTestData.getPolicy(plcy);
                policyMgr.add(policy);
                log.debug(CLS_NM + ".checkQuality name [" + policy.getName() + "] successful");
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".checkQuality caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    public void testAdd()
    {
        // todo: use annotations to pass names of input tables for logging:
        //     public void add(PswdPolicy policy)
        //add("POLICIES_TP1", PolicyTestData.POLICIES_TP1);
        add("ADD " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1"), PolicyTestData.POLICIES_TP1);
        add("ADD " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP2"), PolicyTestData.POLICIES_TP2);
    }

    /**
     * @param msg
     * @param pArray
     */
    public void add(String msg, String[][] pArray)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            for (String[] plcy : pArray)
            {
                PswdPolicy policy = PolicyTestData.getPolicy(plcy);
                policyMgr.add(policy);
                log.debug(CLS_NM + ".add name [" + policy.getName() + "] successful");
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".add caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    public void testUpdate()
    {
        // todo: use annotations to pass names of input tables for logging:
        //     public void update(PswdPolicy policy)
        update("UPD " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1"), PolicyTestData.POLICIES_TP1);
    }

    /**
     * @param msg
     * @param pArray
     */
    public void update(String msg, String[][] pArray)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            for (String[] plcy : pArray)
            {
                PswdPolicy policy = PolicyTestData.getPolicy(plcy);
                policyMgr.update(policy);
                log.debug(CLS_NM + ".update name [" + policy.getName() + "] successful");
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".update caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    public void testDelete()
    {
        // todo: use annotations to pass names of input tables for logging:
        // public void deleteRecursive(String name)
        delete("DEL " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1"), PolicyTestData.POLICIES_TP1);
        delete("DEL " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP2"), PolicyTestData.POLICIES_TP2);
    }

    /**
     * @param msg
     * @param pArray
     */
    public void delete(String msg, String[][] pArray)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            for (String[] plcy : pArray)
            {
                PswdPolicy policy = PolicyTestData.getPolicy(plcy);
                policyMgr.delete(policy);
                log.debug(CLS_NM + ".deleteRecursive name [" + PolicyTestData.getName(plcy) + "] successful");
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".deleteRecursive caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    public void testRead()
    {
        // todo: use annotations to pass names of input tables for logging:
        // public PswdPolicy read(String name)
        read("READ " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1"), PolicyTestData.POLICIES_TP1);
    }

    /**
     * @param msg
     * @param pArray
     */
    public void read(String msg, String[][] pArray)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = PswdPolicyMgrFactory.createInstance();
            for (String[] plcy : pArray)
            {
                PswdPolicy entity = policyMgr.read(PolicyTestData.getName(plcy));
                PolicyTestData.assertEquals(entity, plcy);
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".read caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    public void testSearch()
    {
        // todo: use annotations to pass names of input tables for logging:
        //public List<PswdPolicy> search(String searchVal)
        search("SRCH " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1"), TestUtils.getSrchValue(PolicyTestData.getName(PolicyTestData.POLICIES_TP1[0])), PolicyTestData.POLICIES_TP1);
    }

    /**
     * @param msg
     * @param pArray
     */
    public void search(String msg, String srchValue, String[][] pArray)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = PswdPolicyMgrFactory.createInstance();
            List<PswdPolicy> policies = policyMgr.search(srchValue);
            assertNotNull(policies);
            assertTrue(CLS_NM + "search list size check", pArray.length == policies.size());
            for (String[] plcy : pArray)
            {
                int indx = policies.indexOf(new PswdPolicy(PolicyTestData.getName(plcy)));
                if (indx != -1)
                {
                    PswdPolicy entity = policies.get(indx);
                    assertNotNull(entity);
                    PolicyTestData.assertEquals(entity, plcy);
                }
                else
                {
                    msg = CLS_NM + ".search srchValue [" + srchValue + "] failed list search";
                    LogUtil.logIt(msg);
                    fail(msg);
                }
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".search srchValue [" + srchValue + "] caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    public void testUpdatePasswordPolicy()
    {
        //public void updateUserPolicy(String userId, String policyName)
        updatePasswordPolicy("UPD " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5") + " " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1"), UserTestData.USERS_TU5, PolicyTestData.POLICIES_TP1);
    }

    /**
     * @param msg
     * @param uArray
     * @param pArray
     */
    public void updatePasswordPolicy(String msg, String[][] uArray, String[][] pArray)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            int i = 0;
            for (String[] plcy : pArray)
            {
                policyMgr.updateUserPolicy(UserTestData.getUserId(uArray[i++]), PolicyTestData.getName(plcy));
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".updateUserPolicy caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    public void testDeletePasswordPolicy()
    {
        //public void deletePasswordPolicy(String userId)
        deletePasswordPolicy("DEL " + TestUtils.getDataLabel(UserTestData.class, "USERS_TU5") + " " + TestUtils.getDataLabel(PolicyTestData.class, "POLICIES_TP1"), UserTestData.USERS_TU5);
    }

    /**
     * @param msg
     * @param uArray
     */
    public void deletePasswordPolicy(String msg, String[][] uArray)
    {
        LogUtil.logIt(msg);
        try
        {
            PswdPolicyMgr policyMgr = getManagedPswdMgr();
            for (String[] usr : uArray)
            {
                policyMgr.deletePasswordPolicy(UserTestData.getUserId(usr));
            }
        }
        catch (SecurityException ex)
        {
            log.error(CLS_NM + ".deletePasswordPolicy caught SecurityException errCode=" + ex.getErrorId() + ", msg=" + ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    /**
     *
     * @return
     * @throws SecurityException
     */
    public static PswdPolicyMgr getManagedPswdMgr() throws SecurityException
    {
        PswdPolicyMgr policyMgr = PswdPolicyMgrFactory.createInstance();
        if(FortressJUnitTest.isAdminEnabled() && adminSess == null)
        {
            adminSess = DelegatedMgrImplTest.createAdminSession();
        }
        policyMgr.setAdmin(adminSess);
        return policyMgr;
    }
}
