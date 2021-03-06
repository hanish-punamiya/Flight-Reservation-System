package edu.sjsu.cmpe275.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**  
 * Represents a single flight
 * A flight can have multiple passengers 
 */
@XmlRootElement
@Entity
@Table(name = "flight")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Flight {

    /**  
     * Flight id of a flight object
     * Passed in from createFlight url
     */
    @Id
    @Column(name="flightnumber")
    private long flightNumber;

    /**  
     * Price of a single flight
     */
    @Column(name="price")
    private int price;

    /**  
     * Origin of a single flight
     */
    @Column(name="origin")
    private String origin;

    /**  
     * Destination of a single flight
     */
    @Column(name="destination")
    private String destination;

    /*  Date format: yy-mm-dd-hh, do not include minutes and seconds.
     ** Example: 2017-03-22-19
     **The system only needs to support PST. You can ignore other time zones.
     */

    /**  
     * Departure time as Date object
     */
    @Column(name="departuretime")
    @JsonFormat(pattern="yyyy-MM-dd-HH", timezone="America/Los_Angeles")
    private Date departureTime;

    /**  
     * Arrival time as Date object
     */
    @Column(name="arrivaltime")
    @JsonFormat(pattern="yyyy-MM-dd-HH", timezone="America/Los_Angeles")
    private Date arrivalTime;

    /**  
     * Number of available reservations left on flight
     */
    @Column(name="seatsleft")
    private int seatsLeft;

    /**  
     * Text description of flight
     */
    @Column(name="description")
    private String description;

    /**  
     * Plane object associated with flight.
     * One to one mapping
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "planeid", referencedColumnName = "planeid")
    private Plane plane;

    /**  
     * List of passengers on flight.
     * Many to many mapping.
     */
    @ManyToMany(mappedBy = "flights", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"age","gender","phone","reservations","flights"})
    private List<Passenger> passengers;

    /**  
     * List of reservations associated with flight
     * many to many mapping
     */
    @ManyToMany(mappedBy = "flights", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"passenger","price","flights"})
    private List<Reservation> reservations;

    public Flight(long flightNumber, int price, String origin, String destination, Date departureTime, Date arrivalTime, int seatsLeft, String description, Plane plane, List<Passenger> passengers, List<Reservation> reservations) {
        this.flightNumber = flightNumber;
        this.price = price;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatsLeft = seatsLeft;
        this.description = description;
        this.plane = plane;
        this.passengers = passengers;
        this.reservations = reservations;
    }

    public Flight() {
    }

    public long getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(long flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
