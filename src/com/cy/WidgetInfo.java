package com.cy;

public class WidgetInfo {

	/**
	 * �ؼ�id����
	 */
	private String idName;
	/**
	 * �ؼ�����
	 */
	private String type;
	/**
	 * �������
	 */
	private String content;
	/**
	 * �������ȼ�����ֵ�͵�����ǰ��
	 */
	private int level;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
