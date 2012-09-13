package org.jclouds.nimbula.compute;

import com.google.inject.Module;
import org.jclouds.compute.internal.BaseComputeServiceLiveTest;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.testng.annotations.Test;

@Test(groups = "live")
public class NimbulaComputeServiceLiveTest extends BaseComputeServiceLiveTest {

    public NimbulaComputeServiceLiveTest() {
        provider = "nimbula";
    }

    @Override
    protected Module getSshModule() {
        return new SshjSshClientModule();
    }

}
