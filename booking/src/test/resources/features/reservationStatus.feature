# Author: Jeferson
#Language: en

Feature: Reservation Status Management
As client I want to update the status of reservations so that I can manage my booking effectively.

  Scenario: Update a reservation status to "cancelled"
    Given there is a reservation with ID 1 and status "active"
    When the client updates the status of the reservation with ID 1 to "cancelled"
    Then the reservation with ID 1 should have the status "cancelled"