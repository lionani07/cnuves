package cf;

import java.time.LocalTime;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.ReadableInterval;

public class JodaTimeTest {

	public static void main(String[] args) {		
		Calendar agendaBD = Calendar.getInstance();
		agendaBD.set(2019, 8, 02);
		int year = agendaBD.get(Calendar.YEAR);
		int diaMes = agendaBD.get(Calendar.DAY_OF_MONTH);
		int mes = agendaBD.get(Calendar.MONTH);
		
		
		LocalTime inicio = LocalTime.of(4, 00);
		LocalTime fim = LocalTime.of(7, 00);
		
		LocalTime inicioToSave = LocalTime.of(05, 00);
		LocalTime fimTosave = LocalTime.of(6, 05);
		
		
		DateTime dateTimeInicioDB = new DateTime(year, mes, diaMes, inicio.getHour(), inicio.getMinute());		
		DateTime dateTimeFimDB = new DateTime(year, mes, diaMes, fim.getHour(), fim.getMinute());
		
		DateTime dateTimeIniciotoSave = new DateTime(year, mes, diaMes, inicioToSave.getHour(), inicioToSave.getMinute());		
		DateTime dateTimeFimtoSave = new DateTime(year, mes, diaMes, fimTosave.getHour(), fimTosave.getMinute());
		
		Interval intervalDB = new Interval(dateTimeInicioDB, dateTimeFimDB);
		Interval intervalTosave = new Interval(dateTimeIniciotoSave, dateTimeFimtoSave);
		
		overlap(intervalDB, intervalTosave);
	}
	
	public static void overlap(Interval intervalDB, Interval intervaToSave) {
		ReadableInterval readableInterval = intervaToSave;		
		System.out.println(intervalDB.overlaps(readableInterval));
		
	}

}
