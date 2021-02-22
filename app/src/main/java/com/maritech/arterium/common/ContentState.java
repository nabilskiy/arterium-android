package com.maritech.arterium.common;

public enum ContentState {
	CONTENT, LOADING, EMPTY, ERROR;

	public boolean isContent() {
		return this == CONTENT;
	}

	public boolean isLoading() {
		return this == LOADING;
	}

	public boolean isEmpty() {
		return this == EMPTY;
	}

	public boolean isError() {
		return this == ERROR;
	}
}