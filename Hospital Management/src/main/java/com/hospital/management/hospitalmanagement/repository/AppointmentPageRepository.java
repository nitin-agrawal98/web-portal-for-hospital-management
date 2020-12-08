package com.hospital.management.hospitalmanagement.repository;

import com.hospital.management.hospitalmanagement.model.Appointment;
import com.hospital.management.hospitalmanagement.model.AppointmentPage;
import com.hospital.management.hospitalmanagement.model.AppointmentSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class AppointmentPageRepository {

    private final CriteriaBuilder cb;
    private final EntityManager em;
    private final String[] date;

    public AppointmentPageRepository(EntityManager em) {
        this.em = em;
        this.cb = em.getCriteriaBuilder();
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).split(" ");
    }

    public Page<Appointment> findAllUpcomingWithFilters(AppointmentPage page, AppointmentSearchCriteria criteria, String loginName, String role) {
        CriteriaQuery<Appointment> criteriaQuery = cb.createQuery(Appointment.class);
        Root<Appointment> appointmentRoot = criteriaQuery.from(Appointment.class);

        System.out.println(date[0]);

        if (!role.equals("ADMIN")) {
            criteriaQuery.select(appointmentRoot).where(cb.and(
                    cb.equal(appointmentRoot.join(role.toLowerCase()).get("loginName"), loginName),
                    cb.and(
                            cb.like(appointmentRoot.join(criteria.getFilterBy()).get("firstName"), criteria.getFilterValue() + "%"),
                            cb.and(
                                    cb.like(appointmentRoot.get("healthProblem"), criteria.getHealthProblem() + "%"),
                                    cb.greaterThanOrEqualTo((appointmentRoot.get("date")), date[0])
                            )
                    )
            ));
        } else {
            criteriaQuery.select(appointmentRoot).where(cb.and(
                    cb.like(appointmentRoot.join(criteria.getFilterBy()).get("firstName"), criteria.getFilterValue() + "%"),
                    cb.and(
                            cb.like(appointmentRoot.get("healthProblem"), criteria.getHealthProblem() + "%"),
                            cb.greaterThanOrEqualTo(appointmentRoot.get("date"), date[0])
                    )
            ));
        }

        TypedQuery<Appointment> typedQuery = em.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);
        long appointmentsCount = getUpcomingAppointmentsCount(criteria, loginName, role);

        return new PageImpl<>(typedQuery.getResultList(), pageable, appointmentsCount);
    }

    public Page<Appointment> findAllHistoryWithFilters(AppointmentPage page, AppointmentSearchCriteria criteria, String loginName, String role) {
        CriteriaQuery<Appointment> criteriaQuery = cb.createQuery(Appointment.class);
        Root<Appointment> appointmentRoot = criteriaQuery.from(Appointment.class);

        System.out.println(date[0]);

        if (!role.equals("ADMIN")) {
            criteriaQuery.select(appointmentRoot).where(cb.and(
                    cb.equal(appointmentRoot.join(role.toLowerCase()).get("loginName"), loginName),
                    cb.and(
                            cb.like(appointmentRoot.join(criteria.getFilterBy()).get("firstName"), criteria.getFilterValue() + "%"),
                            cb.and(
                                    cb.like(appointmentRoot.get("healthProblem"), criteria.getHealthProblem() + "%"),
                                    cb.lessThanOrEqualTo((appointmentRoot.get("date")), date[0])
                            )
                    )
            ));
        } else {
            criteriaQuery.select(appointmentRoot).where(cb.and(
                    cb.like(appointmentRoot.join(criteria.getFilterBy()).get("firstName"), criteria.getFilterValue() + "%"),
                    cb.and(
                            cb.like(appointmentRoot.get("healthProblem"), criteria.getHealthProblem() + "%"),
                            cb.lessThanOrEqualTo(appointmentRoot.get("date"), date[0])
                    )
            ));
        }

        TypedQuery<Appointment> typedQuery = em.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);
        long appointmentsCount = getHistoryAppointmentsCount(criteria, loginName, role);

        return new PageImpl<>(typedQuery.getResultList(), pageable, appointmentsCount);
    }

    private long getUpcomingAppointmentsCount(AppointmentSearchCriteria criteria, String loginName, String role) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Appointment> appointmentRoot = countQuery.from(Appointment.class);

        if (!role.equals("ADMIN")) {
            countQuery.select(cb.count(appointmentRoot)).where(cb.and(
                    cb.equal(appointmentRoot.join(role.toLowerCase()).get("loginName"), loginName),
                    cb.and(
                            cb.like(appointmentRoot.join(criteria.getFilterBy()).get("firstName"), criteria.getFilterValue() + "%"),
                            cb.and(
                                    cb.like(appointmentRoot.get("healthProblem"), criteria.getHealthProblem() + "%"),
                                    cb.greaterThanOrEqualTo(appointmentRoot.get("date"), date[0])
                            )
                    )
            ));
        } else {
            countQuery.select(cb.count(appointmentRoot)).where(cb.and(
                    cb.like(appointmentRoot.join(criteria.getFilterBy()).get("firstName"), criteria.getFilterValue() + "%"),
                    cb.and(
                            cb.like(appointmentRoot.get("healthProblem"), criteria.getHealthProblem() + "%"),
                            cb.greaterThanOrEqualTo(appointmentRoot.get("date"), date[0])
                    )
            ));
        }
        return em.createQuery(countQuery).getSingleResult();
    }

    private long getHistoryAppointmentsCount(AppointmentSearchCriteria criteria, String loginName, String role) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Appointment> appointmentRoot = countQuery.from(Appointment.class);

        if (!role.equals("ADMIN")) {
            countQuery.select(cb.count(appointmentRoot)).where(cb.and(
                    cb.equal(appointmentRoot.join(role.toLowerCase()).get("loginName"), loginName),
                    cb.and(
                            cb.like(appointmentRoot.join(criteria.getFilterBy()).get("firstName"), criteria.getFilterValue() + "%"),
                            cb.and(
                                    cb.like(appointmentRoot.get("healthProblem"), criteria.getHealthProblem() + "%"),
                                    cb.lessThanOrEqualTo(appointmentRoot.get("date"), date[0])
                            )
                    )
            ));
        } else {
            countQuery.select(cb.count(appointmentRoot)).where(cb.and(
                    cb.like(appointmentRoot.join(criteria.getFilterBy()).get("firstName"), criteria.getFilterValue() + "%"),
                    cb.and(
                            cb.like(appointmentRoot.get("healthProblem"), criteria.getHealthProblem() + "%"),
                            cb.lessThanOrEqualTo(appointmentRoot.get("date"), date[0])
                    )
            ));
        }
        return em.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(AppointmentPage page) {
        return PageRequest.of(page.getPageNumber(), page.getPageSize());
    }
}
