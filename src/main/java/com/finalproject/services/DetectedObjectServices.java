package com.finalproject.services;

import java.util.Date;
import java.util.List;

import com.finalproject.model.DetectedObject;

public interface DetectedObjectServices {
	public boolean addDetectedObject(DetectedObject detectedObject) throws Exception;
	public DetectedObject getDetectedObjectById(long id) throws Exception;
	public List<DetectedObject> getDetectedObjectList() throws Exception;
	public boolean deleteDetectedObject(long id) throws Exception;
	
	public List<DetectedObject> findByDate(Date date) throws Exception;
	public List<DetectedObject> findByMonth(int Month) throws Exception;
	public List<DetectedObject> findByYear(int Year) throws Exception;
	public List<DetectedObject> findByDatesBetween(Date startDate, Date endDate) throws Exception;

}
