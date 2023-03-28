
//Giancarlo Fruzzetti
// COP 2805C
// Graded Program 4
//2-10-2023


package AppointmentAppP4;


import java.time.*;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

enum Reminder { none, TEXT, EMAIL }


class Contact {
    private StringBuilder name;
    private String email;
    private String phone;

    private Reminder remind;
    private ZoneId zone;

    Contact (String fName, String lName, String email, String phone, Reminder r, ZoneId z) {
        this.name = new StringBuilder();
        this.name.append(fName).append(" ").append(lName);
        this.email = email;
        this.phone = phone;
        this.remind = r;
        this.zone = z;
    } //constructor

    public StringBuilder getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
    public Reminder getReminder()
    {
        return remind;
    }

    public ZoneId getZone()
    {
        return zone;
    }
    @Override
    public String toString() {
        String s = this.name + " email: " + this.email + " phone: " + this.phone + " reminder: " + this.remind + " time zone: " + this.zone;
        return s;
    }
}
class Appointment {

    private Contact contact;
    private String appointment_title;
    private String appointment_desc;
    private ZonedDateTime appointmenttime;
    private ZonedDateTime remindertime;



    // accessor and mutator for user
    public Contact getContact() {
        return contact;
    }

    public String getAppointmentTitle()
    {
        return appointment_title;
    }

    public String getDescription()
    {
        return appointment_desc;
    }

    public ZonedDateTime getZdt()
    {
        return appointmenttime;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setAppointment(String apptitle, String apptdesc, ZonedDateTime appt, ZonedDateTime rm)
    {
        this.appointment_title=apptitle;
        this.appointment_desc=apptdesc;
        this.appointmenttime=appt;
        this.remindertime=rm;
    }

    Appointment() //blank constructor
    {

    }

    public void display() //needed to get all info of the object when called
    {
        System.out.println("\n\nTitle: " + this.appointment_title);
        System.out.println("Description: " + this.appointment_desc);
        System.out.println("Client: " + this.contact.toString());
        System.out.println("Appointment Date and Time: " + this.appointmenttime);
        System.out.println("Reminder Time: " + this.remindertime);
    }



}

public class AppointmentAppP4 implements CalendarReminder{

    public static final Scanner input = new Scanner(System.in); //a global scanner input

    private ArrayList<Appointment> clientappts = new ArrayList<>();

    public AppointmentAppP4() {
    }

    public void runAppts(ZonedDateTime reminder, AppointmentAppP4 A1)
    {
        int timecomparison;
        String Rem;
        ZonedDateTime currentTime;

        for (Appointment A : A1.clientappts)
        {
            currentTime=ZonedDateTime.now();
            timecomparison=currentTime.compareTo(reminder);
            //System.out.println(timecomparison);
            if(timecomparison > 0)
            {
                Rem=A1.buildReminder(A);
                A1.sendReminder(Rem);
            }

        }

    }

    public String buildReminder(Appointment appt) //build reminder and return to main
    {

        //datetime manipulator functions https://docs.oracle.com/javase/10/docs/api/java/time/ZonedDateTime.html#get(java.time.temporal.TemporalField)
        //StringBuilder manipulator functions https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
        StringBuilder S = new StringBuilder("");
        StringBuilder plusses = new StringBuilder("\n");

        String appt_as_string; //returns final string
        LocalDate ldt; //for printing local date
        int hour;  //to print local hour
        int minute; //to print local minute
        int max,min,maxlen,insloc,firststars; //setting string lengths with indexof

        S.append("\n\nSending the following SMS message to " + appt.getContact().getName()+ " " + appt.getContact().getPhone());
        S.append("\n\n+ Hello: " + appt.getContact().getName());
        S.append("\n+");
        S.append("\n+ This is a reminder that you have an upcoming appointment.");
        S.append("\n+ Title:" +appt.getAppointmentTitle());
        S.append("\n+ Description: " + appt.getDescription());
        //day=appt.getZdt().get(ChronoField.DAY_OF_MONTH);
        //year=appt.getZdt().get(ChronoField.YEAR);
        //m=appt.getZdt().getMonth();
        ldt=appt.getZdt().toLocalDate();
        hour=appt.getZdt().getHour();
        minute=appt.getZdt().getMinute();
        S.append("\n+ Date: " + ldt);
        S.append("\n+ Time: " + hour + ":" + minute + " " + appt.getZdt().getZone());
        min=S.indexOf("+ This",0);
        max=S.indexOf("appointment.",0)+12; //beginning + end
        //System.out.println(max+ " " + min);
        maxlen=max-min;
        //System.out.println(maxlen);
        for (int i=0; i < maxlen+1; i++)
        {
            plusses.append("+");
        }
        S.append(plusses);
        insloc=S.indexOf("+ Hello");
        //System.out.println(insloc);
        S.insert(insloc-1,plusses);
        //firststars=S.indexOf("+++")+maxlen;
        //System.out.println(firststars);
        //for(int k=2; k < 6; k++) //put in the plusses
        //{
        //S.insert(insloc+maxlen,'+');
        //}

        appt_as_string=S.toString();
        return appt_as_string;

    }

    public void sendReminder(String reminder)
    {
        System.out.println(reminder);
    }


    public void addAppointments(Appointment... appointments) {
        for (Appointment A : appointments) {
            clientappts.add(A);
        }
    }

    public static int getRandomMonth() {
        // define the range
        int max = 12;
        int min = 1;
        int range = max - min + 1;

        // generate random numbers within 1 to 10
        int rand = (int) (Math.random() * range) + min;
        //System.out.println("Months ahead appt:" + rand); testing random month
        return (rand);

    }

    public static int getRandomHours() {
        // define the range
        int max = 24;
        int min = 2;
        int range = max - min + 1;

        // generate random numbers within 1 to 10
        int rand = (int) (Math.random() * range) + min;
        //System.out.println("hours before reminder:" + rand); testing random hours
        return (rand);

    }


    public static void main(String[] args) {


        int month, currentmonth, currentyr, numberappts;//random appt month, currentmonth, number appts to generate as per user
        ZonedDateTime currentTime, apptdate, appttime, reminder;
        var zone = ZoneId.of("US/Eastern");
        int minushours;
        //String tbf;
        final int appthours = 12; //set offset for the appointment time vs now
        int timecomparison; //is current time later than reminder time
        String Rem;  //for the final reminder string in the box

        //default info
        Reminder R = Reminder.TEXT; //destination of reminder
        Contact client = new Contact("Olivia", "Migiano", "OliviaM@att.net", "904-666-2424", R, zone);
        String apptitle = "Medical Appointment with Dr. IC Spots.";
        String description = "Pending Appointment.";
        AppointmentAppP4 A1 = new AppointmentAppP4();  //object



        System.out.print("Enter number of random appointments for the client: ");
        numberappts = input.nextInt();


        reminder=ZonedDateTime.now(); //set this default to this moment to ensure delivery

        //set appt general information
        for (int i = 0; i < numberappts; i++) //create n random appts
        {
            Appointment clientappt = new Appointment(); //pass info to constructor here
            clientappt.setContact(client); //set the client info
            currentTime = ZonedDateTime.now(); //get current time
            String formattedZdt = currentTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
            ZonedDateTime zoneddatetime = ZonedDateTime.parse(formattedZdt);
            month = AppointmentAppP4.getRandomMonth(); //get random month
            currentmonth = zoneddatetime.getMonthValue();
            currentyr = zoneddatetime.getYear();
            minushours = AppointmentAppP4.getRandomHours();

            //System.out.println(currentTime + " " + month + " " + currentmonth + " " + currentyr + " " + minushours);
            if (month + currentmonth < 12) {
                apptdate = currentTime.plusMonths(month); //set to random month n months into the future
                apptdate = apptdate.plusHours(8);
                //reminder = apptdate.minusHours(minushours);
                //apptdate=ZonedDateTime.now().plusHours(appthours);

                clientappt.setAppointment(apptitle, description, apptdate, reminder);
                A1.addAppointments(clientappt);
            }
            else //overlapped to 13 mos
            {
                apptdate = currentTime.plusYears(1); //add year
                apptdate = apptdate.plusMonths(month + currentmonth - 12); //add month
                apptdate = apptdate.plusHours(8);
                //reminder = apptdate.minusHours(minushours);
                //apptdate=ZonedDateTime.now().plusHours(appthours);

                clientappt.setAppointment(apptitle, description, apptdate, reminder);
                A1.addAppointments(clientappt);

            }
            //var appointmenttime = ZonedDateTime.of(apptdate, appttime, zone);
            //var remindertime = ZonedDateTime.of(reminder, appttime, zone);
            //clientappt.display();
            //System.out.println(apptitle + " " + description + " " + apptdate);
            //System.out.println(appttime + " " + reminder);
        }
        if (A1.clientappts.isEmpty() == false)
        {

           // for (Appointment A : A1.clientappts)
           // {
           //     A.display();

           // }

            A1.runAppts(reminder, A1);
         /*
            for (Appointment A : A1.clientappts)
            {
                currentTime=ZonedDateTime.now();
                timecomparison=currentTime.compareTo(reminder);
                //System.out.println(timecomparison);
                if(timecomparison > 0)
                {
                    Rem=A1.buildReminder(A);
                    A1.sendReminder(Rem);
                }

            } */
        }


    }
}
