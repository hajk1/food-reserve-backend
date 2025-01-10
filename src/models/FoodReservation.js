export class FoodReservation {
  constructor(id, foodId, personId, reservationDate, status) {
    this.id = id;
    this.foodId = foodId;
    this.personId = personId;
    this.reservationDate = reservationDate;
    this.status = status;
  }
}