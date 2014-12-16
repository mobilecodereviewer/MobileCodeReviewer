package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.age.parser;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AgeValueParserTest {
    
    @Test
    public void shouldParseSimpleSecondsFromSForm() throws Exception {
        String ageTextToParse = "\'2s\'";
        AgeValue ageValue = AgeValueParser.parse(ageTextToParse);
        assertEquals(2, ageValue.value());
        assertEquals(AgeType.SECONDS, ageValue.type());
    }

    @Test
    public void shouldParseSimpleSecondsFromSFormAnotherValue() throws Exception {
        String ageTextToParse = "\'5s\'";
        AgeValue ageValue = AgeValueParser.parse(ageTextToParse);
        assertEquals(5, ageValue.value());
        assertEquals(AgeType.SECONDS, ageValue.type() );
    }

    @Test
    public void shouldParseTwoCharacterFormHourFromAnotherValue() throws Exception {
        String ageTextToParse = "\'150hr\'";
        AgeValue ageValue = AgeValueParser.parse(ageTextToParse);
        assertEquals(150, ageValue.value());
        assertEquals(AgeType.HOURS, ageValue.type() );
    }

    @Test
    public void shouldParseThreeCharacterFormMinutesAnotherValue() throws Exception {
        String ageTextToParse = "\'19minute\'";
        AgeValue ageValue = AgeValueParser.parse(ageTextToParse);
        assertEquals(19, ageValue.value());
        assertEquals(AgeType.MINUTES, ageValue.type() );
    }

    @Test
    public void shouldParseFinalFormOfDaysProperly() throws Exception {
        String ageTextToParse = "\'490days\'";
        AgeValue ageValue = AgeValueParser.parse(ageTextToParse);
        assertEquals(490, ageValue.value());
        assertEquals(AgeType.DAYS, ageValue.type() );
    }

    @Test
    public void shouldParseSemiFinalFormOfWeeksProperly() throws Exception {
        String ageTextToParse = "\'99week\'";
        AgeValue ageValue = AgeValueParser.parse(ageTextToParse);
        assertEquals(99, ageValue.value());
        assertEquals(AgeType.WEEKS, ageValue.type() );
    }

    @Test
    public void shouldParseFinalFormOfMonthsProperly() throws Exception {
        String ageTextToParse = "\'56months\'";
        AgeValue ageValue = AgeValueParser.parse(ageTextToParse);
        assertEquals(56, ageValue.value());
        assertEquals(AgeType.MONTHS, ageValue.type() );
    }

    @Test
    public void shouldParseOnCharacterFormOfYearProperly() throws Exception {
        String ageTextToParse = "\'1y\'";
        AgeValue ageValue = AgeValueParser.parse(ageTextToParse);
        assertEquals(1, ageValue.value());
        assertEquals(AgeType.YEARS, ageValue.type() );
    }


}