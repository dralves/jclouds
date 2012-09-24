package org.jclouds.nimbula.features;

import static org.testng.Assert.assertEquals;

import java.util.Properties;
import java.util.Set;

import org.jclouds.nimbula.domain.Shape;
import org.jclouds.nimbula.internal.BaseNimbulaApiLiveTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = "live", singleThreaded = true, enabled = false, testName = "ShapeApiLiveTest")
public class ShapeApiLiveTest extends BaseNimbulaApiLiveTest {

	@BeforeClass
	@Override
	public Properties setupProperties() {

		Properties props = super.setupProperties();
		return props;
	}

	public void testListAndGetShapes() {
		ShapeApi api = context.getApi().getShapeApi();
		Set<Shape> response = api.list("");
		assert null != response;
		for (Shape shape : response) {
			Shape newDetails = api.get(shape.getName());
			assertEquals(newDetails.getCpus(), shape.getCpus());
			assertEquals(newDetails.getIo(), shape.getIo());
			assertEquals(newDetails.getName(), shape.getName());
			assertEquals(newDetails.getProxyuri(), shape.getProxyuri());
			assertEquals(newDetails.getRam(), shape.getRam());
			assertEquals(newDetails.getUri(), shape.getUri());
		}

	}

}
