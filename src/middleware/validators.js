import { body, query, validationResult } from 'express-validator';
import { isValidFutureDate } from '../utils/dateUtils.js';

export const validateDate = [
  query('date')
    .isISO8601()
    .withMessage('Invalid date format. Use ISO 8601 format (YYYY-MM-DD)')
    .custom(isValidFutureDate)
    .withMessage('Date must be today or in the future'),
  (req, res, next) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    next();
  }
];

export const validateReservation = [
  body('foodId').notEmpty().withMessage('Food ID is required'),
  body('personId').notEmpty().withMessage('Person ID is required'),
  body('reservationDate')
    .isISO8601()
    .withMessage('Invalid date format. Use ISO 8601 format (YYYY-MM-DD)')
    .custom(isValidFutureDate)
    .withMessage('Reservation date must be today or in the future'),
  (req, res, next) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    next();
  }
];