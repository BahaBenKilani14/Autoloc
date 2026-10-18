package tn.esprit.autoloc.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc.autoloc_api.domain.Reservation;

public interface IReservationService {
    Reservation create(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findAll();
    Reservation update(Long id, Reservation reservation);
    void deleteById(Long id);
}