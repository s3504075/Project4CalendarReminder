//Giancarlo Fruzzetti
// COP 2805C
// Graded Program 4
//2-10-2023

package AppointmentAppP4;

public interface CalendarReminder {

    // build a reminder using an appointment
    public String buildReminder(Appointment appt);
    // send a reminder using contact's preferred notification method
    public void sendReminder(String reminder);
}
