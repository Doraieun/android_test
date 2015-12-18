package doraieun.TestBootstrap;

import android.accounts.AccountsException;
import android.app.Activity;

import doraieun.TestBootstrap.core.BootstrapService;

import java.io.IOException;

public interface BootstrapServiceProvider {
    BootstrapService getService(Activity activity) throws IOException, AccountsException;
}
