package kz.tech.smartgrades.root.fragments;

import androidx.fragment.app.Fragment;

import kz.tech.smartgrades.authentication.fragments.forgot_password.ForgotPasswordFragment;
import kz.tech.smartgrades.authentication.fragments.registration_mentor.RegistrationMentorFragment;
import kz.tech.smartgrades.authentication.fragments.registration_parent.RegistrationParentFragment;
import kz.tech.smartgrades.authentication.fragments.registration_select.RegistrationSelectFragment;
import kz.tech.smartgrades.authentication.fragments.sign_in.SignInFragment;
import kz.tech.smartgrades.authentication.fragments.splash.SplashFragment;
import kz.tech.smartgrades.authentication.quick_access_sign_out.QuickAccessSignOutFragment;
import kz.tech.smartgrades.childs_module.ChildMainFragment;
import kz.tech.smartgrades.family_room.fragments.access.AccessFragment;
import kz.tech.smartgrades.family_room.fragments.app_lock_list.AppLockListFragment;
import kz.tech.smartgrades.family_room.fragments.child_access.ChildAccessFragment;
import kz.tech.smartgrades.family_room.fragments.family_member_list.FamilyMemberListFragment;
import kz.tech.smartgrades.family_room.fragments.quick_access_sign_in.QuickAccessSignInFragment;
import kz.tech.smartgrades.parents_module.about_app.AboutAppFragment;
import kz.tech.smartgrades.parents_module.add_user.AddUserFragment;
import kz.tech.smartgrades.parents_module.auto_charge.AutoChargeFragment;
import kz.tech.smartgrades.parents_module.cabinet.CabinetFragment;
import kz.tech.smartgrades.parents_module.children_time.ChildrenTimeFragment;
import kz.tech.smartgrades.parents_module.coins.CoinsFragment;
import kz.tech.smartgrades.parents_module.contracts.ContractsFragment;
import kz.tech.smartgrades.parents_module.devices.DevicesFragment;
import kz.tech.smartgrades.parents_module.family_group.FamilyGroupFragment;
import kz.tech.smartgrades.parents_module.family_member.FamilyMemberFragment;
import kz.tech.smartgrades.parents_module.reports.ReportsFragment;
import kz.tech.smartgrades.parents_module.settings.SettingsFragment;
import kz.tech.smartgrades.parents_module.settings.change_password.ChangePasswordFragment;
import kz.tech.smartgrades.parents_module.settings.locality.LocalityFragment;
import kz.tech.smartgrades.parents_module.settings.notification.NotificationFragment;
import kz.tech.smartgrades.parents_module.settings.personal_data.PersonalDataFragment;
import kz.tech.smartgrades.parents_module.settings.pin_code.ChangePinCodeFragment;


public class FragmentSelect {
    public FragmentSelect() { }
    public Fragment getFragment(String fragment) {
        androidx.fragment.app.Fragment fragmentClass = null;
        switch (fragment) {
            case "Splash": fragmentClass = new SplashFragment();  break;

            case "SignInFragment": fragmentClass = new SignInFragment();  break;
            case "ForgotPasswordFragment": fragmentClass = new ForgotPasswordFragment();  break;
            case "RegistrationSelectFragment": fragmentClass = new RegistrationSelectFragment();  break;
            case "RegistrationParentFragment": fragmentClass = new RegistrationParentFragment();  break;
            case "RegistrationMentorFragment": fragmentClass = new RegistrationMentorFragment();  break;





            case "FamilyMemberListFragment": fragmentClass = new FamilyMemberListFragment();  break;
            case "QuickAccessSignInFragment": fragmentClass = new QuickAccessSignInFragment();  break;
            case "QuickAccessSignOutFragment": fragmentClass = new QuickAccessSignOutFragment();  break;
            case "ChildAccessFragment": fragmentClass = new ChildAccessFragment();  break;
            case "AppLockListFragment": fragmentClass = new AppLockListFragment();  break;

            case "AccessFragment": fragmentClass = new AccessFragment();  break;

            //  Parent NavigationView
            case "FamilyGroupFragment": fragmentClass = new FamilyGroupFragment();  break;





            case "FamilyMemberFragment": fragmentClass = new FamilyMemberFragment();  break;
            case "AddUserFragment": fragmentClass = new AddUserFragment();  break;
            case "AboutAppFragment": fragmentClass = new AboutAppFragment();  break;
            case "ReportsFragment": fragmentClass = new ReportsFragment();  break;
            case "SettingsParentFragment": fragmentClass = new SettingsFragment();  break;

            //  Parent View Pager
            case "CabinetFragment": fragmentClass = new CabinetFragment();  break;
            case "CoinsFragment": fragmentClass = new CoinsFragment();  break;
            case "ContractsFragment": fragmentClass = new ContractsFragment();  break;
            case "DevicesFragment": fragmentClass = new DevicesFragment();  break;
            case "TimeChildrenFragment": fragmentClass = new ChildrenTimeFragment();  break;
            case "AutoChargeFragment": fragmentClass = new AutoChargeFragment();  break;



            //  CHILD
            case "ChildMainFragment": fragmentClass = new ChildMainFragment();  break;


            case "PersonalDataFragment": fragmentClass = new PersonalDataFragment();  break;

            case "NotificationFragment": fragmentClass = new NotificationFragment();  break;
            case "LocalityFragment": fragmentClass = new LocalityFragment();  break;
            case "ChangePinCodeFragment": fragmentClass = new ChangePinCodeFragment();  break;
            case "ChangePasswordFragment": fragmentClass = new ChangePasswordFragment();  break;
        //    case "ParentsNav": fragmentClass = new kz.tech.esparta.parents.ParentsNavigationFragment();  break;




        }
        return fragmentClass;
    }


}
