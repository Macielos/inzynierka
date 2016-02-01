package com.explorersguild.test;

import android.test.suitebuilder.TestSuiteBuilder;
import junit.framework.Test;
import junit.framework.TestSuite;

public class MyTestSuite extends TestSuite {
	public static Test suite() {
		return new TestSuiteBuilder(MyTestSuite.class).includeAllPackagesUnderHere().build();
	}
}
