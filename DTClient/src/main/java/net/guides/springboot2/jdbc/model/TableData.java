package net.guides.springboot2.jdbc.model;

/**
 * @author Viashnu.p
 * @since 21-08-2020
 */
public class TableData {
	private static final long serialVersionUID = 1L;

	private int index;
	private String columnName;
	private String columnType;
	private String columnKey;
	private String columnValue;
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}

	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	/**
	 * @return the columnKey
	 */
	public String getColumnKey() {
		return columnKey;
	}

	/**
	 * @param columnKey the columnKey to set
	 */
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	/**
	 * @return the columnValue
	 */
	public String getColumnValue() {
		return columnValue;
	}

	/**
	 * @param columnValue the columnValue to set
	 */
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TableColumns [\ncolumnName = ").append(columnName).append(", \ncolumnType = ")
				.append(columnType).append(", \ncolumnKey = ").append(columnKey).append(", \ncolumnValue = ")
				.append(columnValue).append(" ]");
		return builder.toString();
	}
	
}
