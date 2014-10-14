package com.gopivotal.xd.sqoop;

import org.apache.sqoop.Sqoop;

public class SqoopRunner {

	// sqoop export --connect jdbc:mysql://localhost/sqoop_test --username

	public static void main(String[] args) {

		String[] sqoopArguments = args[0].split(" ");

		final int ret = Sqoop.runTool(sqoopArguments);

		if (ret != 0) {
			throw new RuntimeException("Sqoop failed - return code "
					+ Integer.toString(ret));
		}
	}
}
