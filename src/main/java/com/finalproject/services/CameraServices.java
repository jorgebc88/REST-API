package com.finalproject.services;

import com.finalproject.model.Camera;

import java.util.List;

public interface CameraServices {
	public boolean addCamera(Camera camera) throws Exception;

	public Camera getCameraById(long id) throws Exception;

	public List<Camera> getCameraList() throws Exception;

	public boolean deleteCamera(long id) throws Exception;

	public boolean modifyCamera(long id, boolean active) throws Exception;
}
