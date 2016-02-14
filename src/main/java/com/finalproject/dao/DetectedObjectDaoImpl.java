package com.finalproject.dao;

import com.finalproject.model.DetectedObject;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DetectedObjectDaoImpl implements DetectedObjectDao {

	@Autowired
	SessionFactory sessionFactory;

	Transaction tx = null;

	static final Logger LOGGER = Logger.getLogger(DetectedObjectDaoImpl.class);

	@Override
	public boolean addDetectedObject(DetectedObject detectedObject) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(detectedObject);
		tx.commit();
		session.close();
		LOGGER.info("Object saved: " + detectedObject.toString());
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetectedObject> getDetectedObjectList() throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<DetectedObject> detectedObjectList = session.createCriteria(DetectedObject.class)
				.list();
		tx.commit();
		session.close();
		listSizeVerifier(detectedObjectList);

		return detectedObjectList;
	}

	@Override
	public List<DetectedObject> getDetectedObjectListByCameraId(long cameraId) {
		Session session = sessionFactory.openSession();

		List<DetectedObject> detectedObjectList = session.createQuery("FROM DetectedObject WHERE camera_id = :camera_id")
				.setParameter("camera_id", cameraId)
				.list();
		session.flush();
		session.close();
		listSizeVerifier(detectedObjectList);
		return detectedObjectList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteDetectedObjectBeforeDateByCameraId(Date date, long cameraId) throws Exception {
		Session session = sessionFactory.openSession();

		List<DetectedObject> detectedObjectList = session.createQuery("FROM DetectedObject WHERE date <= :date AND camera_id = :camera_id")
				.setParameter("date", date)
				.setParameter("camera_id", cameraId)
				.list();
		session.flush();
		session.close();
		listSizeVerifier(detectedObjectList);
		return true;
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public List<DetectedObject> findByDateAndCameraId(Date date, long cameraId) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		int day = date.getDate();
		int hour = date.getHours();
		int minutes = date.getMinutes();

		LOGGER.info("Year: " + year + " Month: " + month + " Day: " + day + " Hour: " + hour + " Minute: " + minutes);
		List<DetectedObject> detectedObjectList = session.createQuery("FROM DetectedObject WHERE :year = EXTRACT(YEAR FROM Date) AND :month = EXTRACT(MONTH FROM Date) AND :day = EXTRACT(DAY FROM Date) "
				+ "AND :hour = EXTRACT(HOUR FROM Date) AND :minute = EXTRACT(MINUTE FROM Date)")
				.setParameter("year", year)
				.setParameter("month", month)
				.setParameter("day", day)
				.setParameter("hour", hour)
				.setParameter("minute", minutes)
				.list();
		tx.commit();
		session.close();
		listSizeVerifier(detectedObjectList);
		return detectedObjectList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetectedObject> findByMonthAndCameraId(int month, long cameraId) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<DetectedObject> detectedObjectList = session.createQuery("FROM DetectedObject WHERE camera_id = :camera_id AND :month = EXTRACT(MONTH FROM Date) ")
				.setParameter("camera_id", cameraId)
				.setParameter("month", month)
				.list();
		tx.commit();
		session.close();
		listSizeVerifier(detectedObjectList);
		return detectedObjectList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetectedObject> findByYearAndCameraId(int year, long cameraId) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<DetectedObject> detectedObjectList = session.createQuery("FROM DetectedObject WHERE camera_id = :camera_id AND :year = EXTRACT(YEAR FROM Date) ")
				.setParameter("camera_id", cameraId)
				.setParameter("year", year)
				.list();
		tx.commit();
		session.close();
		listSizeVerifier(detectedObjectList);
		return detectedObjectList;
	}

	@SuppressWarnings({"unchecked", "deprecation"})
	@Override
	public List<DetectedObject> findByDatesBetweenAndCameraId(Date startDate, Date endDate, long cameraId) throws Exception {

		LOGGER.info("Find dates between: " + startDate + " - " + endDate);
		int startHour, startMinutes, endHour, endMinutes;
		startHour = startDate.getHours();
		startMinutes = startDate.getMinutes();
		endHour = endDate.getHours();
		endMinutes = endDate.getMinutes();

		String startHourMinutes, endHourMinutes;

		startHourMinutes = String.format("%02d", startHour) + String.format("%02d", startMinutes);
		endHourMinutes = String.format("%02d", endHour) + String.format("%02d", endMinutes);
		;

		startDate.setHours(0);
		startDate.setMinutes(0);
		endDate.setHours(23);
		endDate.setMinutes(59);

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		List<DetectedObject> detectedObjectList = session.createQuery("FROM DetectedObject WHERE camera_id = :camera_id AND (EXTRACT(HOUR_MINUTE FROM date) BETWEEN :start_hour_minute AND :end_hour_minute) AND date >= :startDate AND date <= :endDate ")
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("start_hour_minute", Integer.parseInt(startHourMinutes))
				.setParameter("end_hour_minute", Integer.parseInt(endHourMinutes))
				.setParameter("camera_id", cameraId)
				.list();
		tx.commit();
		session.close();

		LOGGER.info("Number of detected objects: " + detectedObjectList.size());
		listSizeVerifier(detectedObjectList);
		return detectedObjectList;
	}

	private void listSizeVerifier(List<DetectedObject> detectedObjectList) {
		if(detectedObjectList.size() == 0){
			throw new RuntimeException("No elements were found!");
		}
	}


}
