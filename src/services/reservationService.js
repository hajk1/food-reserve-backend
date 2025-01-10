import { v4 as uuidv4 } from 'uuid';
import { FoodReservation } from '../models/FoodReservation.js';
import { ReservationStatus } from '../constants/enums.js';

export class ReservationService {
  createReservation(reservationData) {
    const reservation = new FoodReservation(
      uuidv4(),
      reservationData.foodId,
      reservationData.personId,
      reservationData.reservationDate,
      ReservationStatus.CONFIRMED
    );
    return reservation;
  }
}