public class Person
{
	//All data associated with a single person
	private int ID;
	private String lastName;
	private String firstName;
	private String vaccineType;
	private String date;
	private String location;


	//Constructor for the Person object
	//public void Person(int ID, String lastName, String firstName,
	//			String VaccineType, String date, String location)
	public Person()
	{
		this.ID = 0;
		this.lastName = null;
		this.firstName = null;
		this.vaccineType = null;
		this.date = null;
		this.location = null;
	}
   
   public Person(int _id, String lName, String fName, String vType, String  _date, String loc)
   {
      ID = _id;
      lastName = lName;
      firstName = fName;
      vaccineType = vType;
      date = _date;
      location = loc;
   }

	//get methods for all data in the Person object
	public int getID()
	{
		return ID;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getVaccineType()
	{
		return vaccineType;
	}

	public String getDate()
	{
		return date;
	}

	public String getLocation()
	{
		return location;
	}

	//set methods (may not be used but here just in case)
	public void setID(int number)
	{
		ID = number;
	}

	public void setLastName(String lastname)
	{
		lastName = lastname;
	}

	public void setFirstName(String firstname)
	{
		firstName = firstname;
	}

	public void setVaccineType(String vaccinetype)
	{
		vaccineType = vaccinetype;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	//toString() method
	public String toString()
	{
		String allInfo = "";
		allInfo = ("ID: " + ID + "\nLast Name: " + lastName + "\nFirst Name: " +
							firstName + "\nVaccine Type: " + vaccineType + "\nDate: " + date +
							"\nLocation: " + location);
		return allInfo;
	}
}
