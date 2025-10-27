package org.example.api;

import org.example.Classes.Reservation;
import org.example.DAO.ReservationDao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    /** GET /reservations */
    @GetMapping
    public List<Reservation> getAll(
            @RequestParam(required = false) Integer tableNumber,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate
    ) throws SQLException {
        if (tableNumber == null && fromDate == null && toDate == null) {
            return reservationDao.getAll();
        }
        return reservationDao.search(tableNumber, fromDate, toDate);
    }

    /** GET /reservations/{id} */
    @GetMapping("/{id}")
    public Reservation getById(@PathVariable int id) throws SQLException {
        Reservation r = reservationDao.getById(id);
        if (r == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        return r;
    }

    /**
     * POST /reservations
     * Body: { "tableNumber": 1, "reservationName": "Smith", "date": "2025-11-10",
     *         "startTime": "19:00:00", "endTime": "21:00:00" }
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation r) throws SQLException {
        boolean clash = reservationDao.overlaps(
                r.getTableNumber(), r.getDate(), r.getStartTime(), r.getEndTime(), null);
        if (clash) throw new ResponseStatusException(HttpStatus.CONFLICT, "Reservation overlaps another");
        return reservationDao.save(r);
    }

    /**
     * PUT /reservations/{id}
     * Body: { "tableNumber": 12, "reservationName": "Smith", "date": "2025-11-10",
     *         "startTime": "19:30:00", "endTime": "21:30:00" }
     */
    @PutMapping("/{id}")
    public Reservation update(@PathVariable int id, @RequestBody Reservation r) throws SQLException {
        if (reservationDao.getById(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");

        r.setReservationId(id);
        boolean clash = reservationDao.overlaps(
                r.getTableNumber(), r.getDate(), r.getStartTime(), r.getEndTime(), id);
        if (clash) throw new ResponseStatusException(HttpStatus.CONFLICT, "Reservation overlaps another");

        return reservationDao.update(r);
    }

    /** DELETE /reservations/{id} */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) throws SQLException {
        if (reservationDao.getById(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");

        reservationDao.delete(id);
    }
}