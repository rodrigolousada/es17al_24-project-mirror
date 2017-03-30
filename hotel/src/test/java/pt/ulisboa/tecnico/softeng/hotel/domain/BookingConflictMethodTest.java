package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BookingConflictMethodTest {
	Booking booking;

	@Before
	public void setUp() {
		Hotel hotel = new Hotel("XPTO123", "Londres");

		// define as instance variables to be reused in the other tests
		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 24);
		this.booking = new Booking(hotel, arrival, departure);
	}

	@Test
	public void noConflictBefore() {
		LocalDate arrival = new LocalDate(2016, 12, 16);
		LocalDate departure = new LocalDate(2016, 12, 19);

		Assert.assertFalse(this.booking.conflict(arrival, departure));
	}

	@Test
	public void noConflictAfter() {
		LocalDate arrival = new LocalDate(2016, 12, 24);
		LocalDate departure = new LocalDate(2016, 12, 30);

		Assert.assertFalse(this.booking.conflict(arrival, departure));
	}

	// Cases where arrival is before the already booked arrival
	@Test
	public void conflictArrivalBeforeArrival_DepartureBetweenArrivalAndDeparture() {
		LocalDate arrival = new LocalDate(2016, 12, 17);
		LocalDate departure = new LocalDate(2016, 12, 20);

		Assert.assertTrue(this.booking.conflict(arrival, departure));
	}

	@Test
	public void conflictArrivalBeforeArrival_DepartureAfterDeparture() {
		LocalDate arrival = new LocalDate(2016, 12, 15);
		LocalDate departure = new LocalDate(2016, 12, 26);

		Assert.assertTrue(this.booking.conflict(arrival, departure));

	}

	@Test
	public void conflictArrivalBeforeArrival_DepartureOverlap() {
		LocalDate arrival = new LocalDate(2016, 12, 20);
		LocalDate departure = new LocalDate(2016, 12, 24);

		Assert.assertTrue(this.booking.conflict(arrival, departure));
	}

	// Cases with arrival between the already booked arrival and departure
	@Test
	public void conflictArrivalAfterArrival_DepartureBeforeDeparture() {
		LocalDate arrival = new LocalDate(2016, 12, 20);
		LocalDate departure = new LocalDate(2016, 12, 20);

		Assert.assertTrue(this.booking.conflict(arrival, departure));
	}

	@Test
	public void conflictArrivalAfterArrival_DepartureAfterDeparture() {
		LocalDate arrival = new LocalDate(2016, 12, 20);
		LocalDate departure = new LocalDate(2016, 12, 26);

		Assert.assertTrue(this.booking.conflict(arrival, departure));

	}

	@Test
	public void conflictArrivalAfterArrival_DepartureOverlap() {
		LocalDate arrival = new LocalDate(2016, 12, 20);
		LocalDate departure = new LocalDate(2016, 12, 24);

		Assert.assertTrue(this.booking.conflict(arrival, departure));
	}

	// Cases with arrival overlapping the already booked arrival
	@Test
	public void conflictArrivalOverlap_DepartureBeforeDeparture() {
		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 20);

		Assert.assertTrue(this.booking.conflict(arrival, departure));
	}

	@Test
	public void conflictArrivalOverlap_DepartureAfterDeparture() {
		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 26);

		Assert.assertTrue(this.booking.conflict(arrival, departure));
	}

	@Test
	public void conflictArrivalAndDepartureOverlap() {
		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 24);

		Assert.assertTrue(this.booking.conflict(arrival, departure));
	}

	// case where arrival provided is after departure
	@Test(expected = HotelException.class)
	public void conflictDepartureBeforeArrival() {
		LocalDate arrival = new LocalDate(2016, 12, 10);
		LocalDate departure = new LocalDate(2016, 12, 8);

		this.booking.conflict(arrival, departure);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
