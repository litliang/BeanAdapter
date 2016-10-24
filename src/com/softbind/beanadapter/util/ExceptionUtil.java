package com.softbind.beanadapter.util;

public class ExceptionUtil {

	public ExceptionUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceptionUtil(boolean throwThanCatch) {
		super();
		this.throwThanCatch = throwThanCatch;
	}

	private boolean throwThanCatch = false;

	public void check(Object object, String express) {
		if (object instanceof Number) {
			express = express.replaceAll(" ", "");
			String comparator = express.substring(0, 1);
			Double value = new Double(express.substring(1));

			double result = new Double(object.toString()).doubleValue()
					- value.doubleValue();
			if (comparator.equals(">")) {
				if (!(result > value)) {
					startException(object.toString(), express);
				}
			} else if (comparator.equals(">=")) {
				if (!(result >= value)) {
					startException(object.toString(), express);
				}
			} else if (comparator.equals("<")) {
				if (!(result < value)) {
					startException(object.toString(), express);
				}
			} else if (comparator.equals("<=")) {
				if (!(result <= value)) {
					startException(object.toString(), express);
				}
			} else if (comparator.equals("==")) {
				if (!(result == value)) {
					startException(object.toString(), express);
				}
			} else {
				startException("", "invalid operator on Number");
			}
		}

	}

	public void checkNotNull(Object... objects) {
		if (objects != null) {
			for (Object object : objects) {
				if (!(object instanceof Number || object instanceof Boolean)) {
					if (object == null) {
						startException(object.toString(), "is a null value");
					}
				}
			}
		}

	}

	private void startException(String value, String message) {
		if (throwThanCatch) {
			throw new RuntimeException("rule violated: " + value + message);
		} else {
			new RuntimeException("rule violated: " + value + message)
					.printStackTrace();
		}

	}
}
