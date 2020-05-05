package com.topjava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ListStorageTest.class, ArrayStorageTest.class, MapStorageTest.class, SortedArrayStorageTest.class, UuidMapStorageTest.class})

public class AllStorageTest {
}
