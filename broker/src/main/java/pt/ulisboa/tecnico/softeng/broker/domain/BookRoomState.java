package pt.ulisboa.tecnico.softeng.broker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BookRoomState extends AdventureState {
	private static Logger logger = LoggerFactory.getLogger(ConfirmedState.class);
	
	@Override
	public State getState() {
		return State.BOOK_ROOM;
	}

	@Override
	public void process(Adventure adventure) {
		logger.debug("process");
		try {
			adventure.setRoomConfirmation(HotelInterface.reserveRoom(Room.Type.SINGLE, adventure.getBegin(), adventure.getEnd()));
		} catch (HotelException rae) {
			adventure.setState(new UndoState());
			return;
		} catch (RemoteAccessException rae) {
			this.incNumOfRemoteErrors();	// increment number of errors
			if(this.getNumOfRemoteErrors()==5)	// if (number of errors == 10)
				adventure.setState(new UndoState());
			return;
		}

		adventure.setState(new ConfirmedState());
	}
	
}
