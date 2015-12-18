package doraieun.TestBootstrap;

import doraieun.TestBootstrap.authenticator.BootstrapAuthenticatorActivity;
import doraieun.TestBootstrap.core.TimerService;
import doraieun.TestBootstrap.ui.BootstrapActivity;
import doraieun.TestBootstrap.ui.BootstrapFragmentActivity;
import doraieun.TestBootstrap.ui.BootstrapTimerActivity;
import doraieun.TestBootstrap.ui.CheckInsListFragment;
import doraieun.TestBootstrap.ui.MainActivity;
import doraieun.TestBootstrap.ui.NavigationDrawerFragment;
import doraieun.TestBootstrap.ui.NewsActivity;
import doraieun.TestBootstrap.ui.NewsListFragment;
import doraieun.TestBootstrap.ui.UserActivity;
import doraieun.TestBootstrap.ui.UserListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AndroidModule.class,
                BootstrapModule.class
        }
)
public interface BootstrapComponent {

    void inject(BootstrapApplication target);

    void inject(BootstrapAuthenticatorActivity target);

    void inject(BootstrapTimerActivity target);

    void inject(MainActivity target);

    void inject(CheckInsListFragment target);

    void inject(NavigationDrawerFragment target);

    void inject(NewsActivity target);

    void inject(NewsListFragment target);

    void inject(UserActivity target);

    void inject(UserListFragment target);

    void inject(TimerService target);

    void inject(BootstrapFragmentActivity target);
    void inject(BootstrapActivity target);


}
