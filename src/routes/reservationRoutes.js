import express from 'express';
import { ReservationService } from '../services/reservationService.js';
import { validateReservation } from '../middleware/validators.js';

const router = express.Router();
const reservationService = new ReservationService();

router.post('/', validateReservation, (req, res) => {
  const reservation = reservationService.createReservation(req.body);
  res.status(201).json(reservation);
});

export default router;