package pl.edu.agh.mobilecodereviewer.model.utilities;

import org.junit.Test;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQuery;
import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParser;

import java.util.*;

import static junit.framework.TestCase.assertEquals;

public class GerritQueryParserTest {

    @Test
    public void shouldProducedQueryForNullQueryStringNullInputReturnNull() throws Exception {
        GerritQuery query = GerritQueryParser.parse(null);
        assertEquals(null, query.execute(null));
    }

    @Test
    public void shouldProduceQueryForNonNullQueryStringNullInputReturnNull() {
        GerritQuery query = GerritQueryParser.parse("");
        assertEquals(null, query.execute(null));
    }

    @Test
    public void shouldProduceQueryForNonNullQueryStringNonNullInputReturnSameList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("");
        LinkedList<ChangeInfo> expectedEmptyList = new LinkedList<>();
        assertEquals(expectedEmptyList, query.execute(new LinkedList<ChangeInfo>()));
    }

    private ChangeInfo createAbandonedChange() {
        return createAbandonedChange("NOT_RELEVANT");
    }

    private ChangeInfo createAbandonedChange(String id) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setStatus(ChangeStatus.ABANDONED );
        return changeInfo;
    }

    private ChangeInfo createMergedChange() {
        return createMergedChange("NOT_RELEVANT");
    }

    private ChangeInfo createMergedChange(String id) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setStatus(ChangeStatus.MERGED );
        return changeInfo;
    }

    private ChangeInfo createOpenChange(String id) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setStatus(ChangeStatus.NEW);
        return changeInfo;
    }


    private ChangeInfo createDraftChange(String id) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setStatus(ChangeStatus.DRAFT);
        return changeInfo;
    }

    @Test
    public void shouldProduceStatusOpenQueryForProperStringAndINputWithoutOpenChangesReturnEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:open");
        List<ChangeInfo> expectedEmptyList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange(),createAbandonedChange(),createMergedChange() );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceNOTStatusOpenQueryForProperStringAndINputWithoutOpenChangesReturnNOTEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("(  NOT (  status:open  ) )");
        List<ChangeInfo> expectedEmptyList = Arrays.asList(createMergedChange(),createAbandonedChange(),createMergedChange() );
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange(),createAbandonedChange(),createMergedChange() );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceNegatedStatusOpenQueryForProperStringAndINputWithoutOpenChangesReturnFewElemList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("-status:open");
        List<ChangeInfo> expectedEmptyList = Arrays.asList(createMergedChange(),createAbandonedChange(),createMergedChange() );
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange(),createAbandonedChange(),createMergedChange() );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceStatusOpenQueryForProperStringAndINputWithoutOpenChangesReturnEmptyListCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("(        status:open      )     ");
        List<ChangeInfo> expectedEmptyList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange(),createAbandonedChange(),createMergedChange() );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceStatusOpenQueryForProperStringAndInputWithOneOpenChangesReturnListWIthOpenChange() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:open");
        List<ChangeInfo> expectedEmptyList = Arrays.asList(createOpenChange("1"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange(),createOpenChange("1"),createAbandonedChange(),createMergedChange() );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceStatusOpenQueryForProperStringAndInputWithOneOpenChangesReturnListWIthOpenChangeCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("(            status:open            )");
        List<ChangeInfo> expectedEmptyList = Arrays.asList(createOpenChange("1"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange(),createOpenChange("1"),createAbandonedChange(),createMergedChange() );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceStatusAllQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningAllChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:all");
        List<ChangeInfo> expectedEmptyList = Arrays.asList(createMergedChange(),createOpenChange("1"),createAbandonedChange(),createMergedChange() );
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange(),createOpenChange("1"),createAbandonedChange(),createMergedChange() );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceStatusAllQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningAllChangesCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:all OR status:merged");
        List<ChangeInfo> expectedEmptyList = Arrays.asList(createMergedChange(),createOpenChange("1"),createAbandonedChange(),createMergedChange() );
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange(),createOpenChange("1"),createAbandonedChange(),createMergedChange() );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceMergedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningMergedChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:merged");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createMergedChange("1") , createMergedChange("2"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange(),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceMergedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningMergedChangesCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:all AND status:merged");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createMergedChange("1") , createMergedChange("2"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange(),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceNOTMergedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningMergedChangesCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:all AND NOT status:merged");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createOpenChange("1"),createAbandonedChange() );
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange(),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceIsMergedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningMergedChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("is:merged");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createMergedChange("1") , createMergedChange("2"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange(),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceNegatedIsMergedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningNotMergedChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("-is:merged");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createOpenChange("1"), createAbandonedChange());
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange(),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceIsMergedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningMergedChangesCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("is:merged OR status:abandoned");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createMergedChange("1") ,createAbandonedChange(), createMergedChange("2"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange(),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceNegatedIsMergedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningMergedChangesCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("-is:merged OR status:abandoned");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createOpenChange("1") ,createAbandonedChange() );
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange(),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceNegatedIsMergedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningMergedChangesCase2WithSpacesParams() throws Exception {
        GerritQuery query = GerritQueryParser.parse(" ((  -is:merged) OR (status:abandoned  )    )");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createOpenChange("1") ,createAbandonedChange() );
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange(),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceIsMergedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningMergedChangesCase3() throws Exception {
        GerritQuery query = GerritQueryParser.parse("(  (  is:merged  ) OR (  status:abandoned  )   )");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createMergedChange("1") ,createAbandonedChange(), createMergedChange("2"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange(),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }


    @Test
    public void shouldProduceAbandonedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningAbandonedChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:abandoned");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createAbandonedChange("5"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange("5"),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceAbandonedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningAbandonedChangesCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("(status:merged OR status:abandoned) OR status:open");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createMergedChange("1"),createOpenChange("1"),createAbandonedChange("5"),createMergedChange("2"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange("5"),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceNegatedAbandonedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningAbandonedChangesCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("(status:merged OR status:abandoned) OR -status:open");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createMergedChange("1"),createAbandonedChange("5"),createMergedChange("2"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange("5"),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceIsAbandonedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningAbandonedChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("is:abandoned");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createAbandonedChange("5"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createAbandonedChange("5"),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    private ChangeInfo createSubmittedChange(String id) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setStatus(ChangeStatus.SUBMITTED);
        return changeInfo;
    }

    @Test
    public void shouldProduceSubmittedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningSubmittedChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:submitted");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createSubmittedChange("5"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createSubmittedChange("5"),createAbandonedChange("5"),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceSubmittedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningSubmittedChangesCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:submitted OR NOT(status:submitted OR status:open)");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createMergedChange("1"),createSubmittedChange("5"),createAbandonedChange("5"),createMergedChange("2"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createSubmittedChange("5"),createAbandonedChange("5"),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceSubmittedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningSubmittedChangesCase3() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:abandoned OR status:submitted OR status:open");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createOpenChange("1"),createSubmittedChange("5"),createAbandonedChange("5"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createSubmittedChange("5"),createAbandonedChange("5"),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceIsSubmittedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningSubmittedChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("is:submitted");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createSubmittedChange("5"));
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createSubmittedChange("5"),createAbandonedChange("5"),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceIsSubmittedQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningSubmittedChangesCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("is:submitted AND status:merged");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( );
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createSubmittedChange("5"),createAbandonedChange("5"),createMergedChange("2") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }



    @Test
    public void shouldProduceIsDraftQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningDraftChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("is:draft");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createDraftChange("10") , createDraftChange("3") );
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createDraftChange("10"),
                                                   createAbandonedChange("5"),createMergedChange("2") ,createDraftChange("3") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceIsDraftQueryForProperStringAndInputWithFewOpenAndCloseChangesReturningDraftChangesCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("is:draft OR status:abandoned");
        List<ChangeInfo> expectedEmptyList = Arrays.asList( createDraftChange("10") ,createAbandonedChange("5"), createDraftChange("3") );
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange("1"),createOpenChange("1"),createDraftChange("10"),
                createAbandonedChange("5"),createMergedChange("2") ,createDraftChange("3") );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProduceIsOpenQueryForProperStringAndInputWithOneOpenChangesReturnListWIthOpenChange() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:merged OR is:open");
        List<ChangeInfo> expectedEmptyList = Arrays.asList(createMergedChange(), createOpenChange("1"), createMergedChange());
        List<ChangeInfo> inputList = Arrays.asList(createMergedChange(),createOpenChange("1"),createAbandonedChange(),createMergedChange() );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    private ChangeInfo createChangeWithGivenUpdateDate(String id, Date date) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setUpdated(date);
        return changeInfo;
    }
    
    @Test
    public void shouldProductAge50MinutesQueryForProperStringAndInputWithOneOutdatedChangeReturnEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("age:\'50m\'");
        List<ChangeInfo> expectedEmptyList = new LinkedList<>();
        Calendar cal = Calendar.getInstance();cal.add(Calendar.MINUTE, -60);
        Date dateBackInHalfHour= cal.getTime();
        List<ChangeInfo> inputList = Arrays.asList(createChangeWithGivenUpdateDate("1", dateBackInHalfHour) );
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProductAge30MinutesQueryForProperStringAndInputWithOneProperDatedChangeReturnSingletonList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("age:\'50m\'");
        Calendar cal = Calendar.getInstance();cal.add(Calendar.MINUTE,-30);
        Date dateBackInHalfHour= cal.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", dateBackInHalfHour);

        List<ChangeInfo> expectedSingletonList = Arrays.asList(changeWithGivenUpdateDate);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedSingletonList, query.execute(inputList));
    }

    @Test
    public void shouldProductAge30MinutesQueryForProperStringAndInputWithOneProperDatedChangeReturnEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("age:\'50m\' AND age:'20m'");
        Calendar cal = Calendar.getInstance();cal.add(Calendar.MINUTE,-30);
        Date dateBackInHalfHour= cal.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", dateBackInHalfHour);

        List<ChangeInfo> expectedEmptyList = Arrays.asList();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldProductAge30MinutesQueryForProperStringAndInputWithOneProperDatedChangeReturnEmptyListCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("age:\'50m\' age:'20m'");
        Calendar cal = Calendar.getInstance();cal.add(Calendar.MINUTE,-30);
        Date dateBackInHalfHour= cal.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", dateBackInHalfHour);

        List<ChangeInfo> expectedEmptyList = Arrays.asList();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldAgeFilterProperChangesTestCase1() throws Exception {
        GerritQuery query = GerritQueryParser.parse("age:\'3d\'");
        Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR,-1);
        Date dateBackOneDay= cal.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", dateBackOneDay);

        List<ChangeInfo> expectedSingletonList = Arrays.asList(changeWithGivenUpdateDate);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedSingletonList, query.execute(inputList));
    }

    @Test
    public void shouldAgeFilterProperChangesTestCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("age:\'3d\' OR status:abandoned");
        Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR,-4);
        Date dateBackFourDay= cal.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", dateBackFourDay);
        ChangeInfo changeInfo = createAbandonedChange("2");

        List<ChangeInfo> expectedEmptyList = Arrays.asList( createAbandonedChange("2") );
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate,createAbandonedChange("2"));
        assertEquals(expectedEmptyList, query.execute(inputList));
    }

    @Test
    public void shouldAgeFilterProperChangesTestCase3() throws Exception {
        GerritQuery query = GerritQueryParser.parse("age:\'5y\' OR status:abandoned");
        Calendar cal = Calendar.getInstance();cal.add(Calendar.YEAR,-4);
        Date dateBackOneYear= cal.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", dateBackOneYear);

        Calendar cal2 = Calendar.getInstance();cal2.add(Calendar.YEAR,-6);
        Date dateBackFiveYears= cal2.getTime();
        ChangeInfo changeWithGivenUpdateDate2 = createChangeWithGivenUpdateDate("2", dateBackFiveYears);

        List<ChangeInfo> expectedSingletonList = Arrays.asList(createAbandonedChange("2"),changeWithGivenUpdateDate);
        List<ChangeInfo> inputList =
                Arrays.asList(createAbandonedChange("2"),changeWithGivenUpdateDate,changeWithGivenUpdateDate2);
        assertEquals(expectedSingletonList, query.execute(inputList));
    }


    @Test
    public void shouldBeforeFilterChangesAfterSpecifiedDateReturningEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("before:\'2014-10-25\'");
        Calendar instance = Calendar.getInstance();
        Date today = instance.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", today);

        List<ChangeInfo> expectedList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldNOTBeforeFilterChangesAfterSpecifiedDateReturningNotEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("NOT before:\'2014-10-25\'");
        Calendar instance = Calendar.getInstance();
        Date today = instance.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", today);

        List<ChangeInfo> expectedList = Arrays.asList(changeWithGivenUpdateDate);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldBeforeFilterChangesAfterSpecifiedDateReturningEmptyListCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("before:\'2014-10-25\' AND status:merged");
        Calendar instance = Calendar.getInstance();
        Date today = instance.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", today);

        List<ChangeInfo> expectedList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldBeforeFilterChangesBeforeSpecifiedDateReturningSingletonList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("before:\'2014-10-25\'");
        Calendar instance = Calendar.getInstance();instance.add(Calendar.MONTH,-1);
        Date today = instance.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", today);

        List<ChangeInfo> expectedList = Arrays.asList(changeWithGivenUpdateDate);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldUntilFilterChangesBeforeSpecifiedDateReturningSingletonList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("until:\'2014-10-25\'");
        Calendar instance = Calendar.getInstance();instance.add(Calendar.MONTH,-1);
        Date today = instance.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", today);

        List<ChangeInfo> expectedList = Arrays.asList(changeWithGivenUpdateDate);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAfterFilterChangesAfterSpecifiedDateReturningSingletonList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("after:\'2014-10-25\'");
        Calendar instance = Calendar.getInstance();
        Date today = instance.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", today);

        List<ChangeInfo> expectedList = Arrays.asList( changeWithGivenUpdateDate );
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAfterFilterChangesBeforeSpecifiedDateReturningNullList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("after:\'2014-10-25\'");
        Calendar instance = Calendar.getInstance();instance.add(Calendar.MONTH,-1);
        Date today = instance.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", today);

        List<ChangeInfo> expectedList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldSinceFilterChangesBeforeSpecifiedDateReturningNullList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("since:\'2014-10-25\'");
        Calendar instance = Calendar.getInstance();instance.add(Calendar.MONTH,-1);
        Date today = instance.getTime();
        ChangeInfo changeWithGivenUpdateDate = createChangeWithGivenUpdateDate("1", today);

        List<ChangeInfo> expectedList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUpdateDate);
        assertEquals(expectedList, query.execute(inputList));
    }

    private ChangeInfo createChangeInfoWithId(String id) {
        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.setChangeId(id);
        return changeInfo;
    }

    private ChangeInfo createChangeInfoWithIdAndStatus(String id,String statusName) {
        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.setChangeId(id);
        changeInfo.setStatus(ChangeStatus.createStatusFromString(statusName));
        return changeInfo;
    }
    
    @Test
    public void shouldChangeIdFilteredOutChangesWithOtherId() throws Exception {
        GerritQuery query = GerritQueryParser.parse("change:\'15\'");
        ChangeInfo changeWithGivenId = createChangeInfoWithId("10");

        List<ChangeInfo> expectedList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenId);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldChangeIdFilteredOutChangesWithOtherIdCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("change:\'15\' OR status:open");
        ChangeInfo changeWithGivenId = createChangeInfoWithIdAndStatus("10", "NEW");

        List<ChangeInfo> expectedList = Arrays.asList(changeWithGivenId);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenId);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldChangeIdFilterChangesWithGivenId() throws Exception {
        GerritQuery query = GerritQueryParser.parse("change:\'10\'");
        ChangeInfo changeWithGivenId = createChangeInfoWithId("10");

        List<ChangeInfo> expectedList = Arrays.asList(changeWithGivenId);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenId);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldChangeIdFilterChangesWithGivenIdCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("change:\'10\' AND status:open");
        ChangeInfo changeWithGivenId = createChangeInfoWithIdAndStatus("10", "ABANDONED");

        List<ChangeInfo> expectedList = Arrays.asList();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenId);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldChangeIdFilterChangesWithGivenIdCase3() throws Exception {
        GerritQuery query = GerritQueryParser.parse("change:\'10\' OR status:open");
        ChangeInfo changeWithGivenId = createChangeInfoWithIdAndStatus("10","ABANDONED");

        List<ChangeInfo> expectedList = Arrays.asList(changeWithGivenId);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenId);
        assertEquals(expectedList, query.execute(inputList));
    }

    private ChangeInfo createChangeInfoFromUser(String user) {
        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.setOwnerName(user);
        return changeInfo;
    }

    private ChangeInfo createChangeInfoFromUserChangeIdAndStatus(String user,String changeId,String status) {
        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.setOwnerName(user);
        changeInfo.setChangeId(changeId);
        changeInfo.setStatus(ChangeStatus.createStatusFromString(status));
        return changeInfo;
    }

    @Test
    public void shouldOwnerFilterOutChangesWithOwnerDifferent() throws Exception {
        GerritQuery query = GerritQueryParser.parse("owner:\'Piotr\'");
        ChangeInfo changeWithGivenUser = createChangeInfoFromUser("Kacper");

        List<ChangeInfo> expectedList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUser);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldOwnerFilterChangesWithOwnerSame() throws Exception {
        GerritQuery query = GerritQueryParser.parse("(owner:\'Piotr\' OR change:'10')AND status:open");
        ChangeInfo changeWithGivenUser = createChangeInfoFromUserChangeIdAndStatus("Piotr","10","MERGED");

        List<ChangeInfo> expectedList = Arrays.asList();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUser);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldOwnerFilterChangesWithOwnerSameCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("change:'10' OR (owner:'Piotr' status:merged)");
        ChangeInfo changeWithGivenUser = createChangeInfoFromUserChangeIdAndStatus("Piotr","20","MERGED");

        List<ChangeInfo> expectedList = Arrays.asList(changeWithGivenUser);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUser);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldOwnerFilterChangesWithOwnerSameCase3() throws Exception {
        GerritQuery query = GerritQueryParser.parse("change:'10' OR (owner:'Piotr' status:open)");
        ChangeInfo changeWithGivenUser = createChangeInfoFromUserChangeIdAndStatus("Piotr","10","MERGED");

        List<ChangeInfo> expectedList = Arrays.asList(changeWithGivenUser);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUser);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldOwnerFilterChangesWithOwnerSameCase4() throws Exception {
        GerritQuery query = GerritQueryParser.parse("change:'10' OR (owner:'Piotr' status:open)");
        ChangeInfo changeWithGivenUser = createChangeInfoFromUserChangeIdAndStatus("Piotr","20","MERGED");

        List<ChangeInfo> expectedList = Arrays.asList();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUser);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldNOTOwnerFilterChangesWithOwnerSameCase4() throws Exception {
        GerritQuery query = GerritQueryParser.parse("change:'10' OR NOT(owner:'Piotr' status:open)");
        ChangeInfo changeWithGivenUser = createChangeInfoFromUserChangeIdAndStatus("Piotr","20","MERGED");

        List<ChangeInfo> expectedList = Arrays.asList(changeWithGivenUser);
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUser);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldOFilterChangesWithOwnerSame() throws Exception {
        GerritQuery query = GerritQueryParser.parse("o:\'Piotr\'");
        ChangeInfo changeWithGivenUser = createChangeInfoFromUser("Piotr");

        List<ChangeInfo> expectedList = Arrays.asList(createChangeInfoFromUser("Piotr"));
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUser);
        assertEquals(expectedList, query.execute(inputList));
    }

    public ChangeInfo createChangeInfoFromProject(String project) {
        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.setProject(project);
        return changeInfo;
    }

    @Test
    public void shouldProjectsFilterOutChangesWithProjectsNotPrefixed() throws Exception {
        GerritQuery query = GerritQueryParser.parse("projects:\'PRO\'");
        ChangeInfo changeWithGivenUser = createChangeInfoFromProject("LOL");

        List<ChangeInfo> expectedList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUser);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldProjectsFilterChangesWithProjectsPrefixed() throws Exception {
        GerritQuery query = GerritQueryParser.parse("projects:\'PRO\'");
        ChangeInfo changeWithGivenUser = createChangeInfoFromProject("PRO_PROJECT");

        List<ChangeInfo> expectedList = Arrays.asList(createChangeInfoFromProject("PRO_PROJECT"));
        List<ChangeInfo> inputList = Arrays.asList(changeWithGivenUser);
        assertEquals(expectedList, query.execute(inputList));
    }

    private ChangeInfo createChangeInfoFromAddedLines(int linesAdded) {
        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.setInsertions(linesAdded);
        return changeInfo;
    }
    
    @Test
    public void shouldAddedFilterAppropriateChangesWithNotEqualLinesWithGivenReturnsEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("added:\'50\'");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(30);

        List<ChangeInfo> expectedList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedEqualFilterAppropriateChangesWithEqualLinesWithGivenReturnsEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("added:\'50\'");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(50);

        List<ChangeInfo> expectedList = Arrays.asList( createChangeInfoFromAddedLines(50) );
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedMoreFilterAppropriateChangesWithMoreLinesWithGivenReturnsSingletonList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("added:\'>30\'");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(50);

        List<ChangeInfo> expectedList = Arrays.asList( createChangeInfoFromAddedLines(50) );
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedMoreFilterAppropriateChangesWithMoreLinesWithGivenReturnsEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("added:\'>90\'");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(50);

        List<ChangeInfo> expectedList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedMoreEqualFilterAppropriateChangesWithMoreLinesWithGivenReturnsSingletonList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("added:\'>=50\'");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(50);

        List<ChangeInfo> expectedList = Arrays.asList( createChangeInfoFromAddedLines(50) );
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldNotAddedMoreEqualFilterAppropriateChangesWithMoreLinesWithGivenReturnsEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("NOT added:\'>=50\'");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(50);

        List<ChangeInfo> expectedList = Arrays.asList(  );
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedMoreEqualFilterAppropriateChangesWithMoreLinesWithGivenReturnsTestCase1() throws Exception {
        GerritQuery query = GerritQueryParser.parse("added:\'>=50\' OR added:'<20'");

        ChangeInfo changeWithAddedLines1 = createChangeInfoFromAddedLines(50);
        ChangeInfo changeWithAddedLines2 = createChangeInfoFromAddedLines(10);

        List<ChangeInfo> expectedList = Arrays.asList( changeWithAddedLines1,changeWithAddedLines2 );
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines1,changeWithAddedLines2);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedLessEqualFilterAppropriateChangesWithMoreLinesWithGivenReturnsSingletonList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("added:\'<=50\'");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(50);

        List<ChangeInfo> expectedList = Arrays.asList( createChangeInfoFromAddedLines(50) );
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedLessFilterAppropriateChangesWithMoreLinesWithGivenReturnsSingletonList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("added:\'<90\'");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(50);

        List<ChangeInfo> expectedList = Arrays.asList( createChangeInfoFromAddedLines(50) );
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedNOTLessFilterAppropriateChangesWithMoreLinesWithGivenReturnsNotSingletonList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("NOT(added:\'<90\')");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(50);

        List<ChangeInfo> expectedList = Arrays.asList(  );
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedLessFilterAppropriateChangesWithMoreLinesWithGivenReturnsEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("added:\'<20\'");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(50);

        List<ChangeInfo> expectedList = new LinkedList<>();
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedNOTLessFilterAppropriateChangesWithMoreLinesWithGivenReturnsNOTEmptyList() throws Exception {
        GerritQuery query = GerritQueryParser.parse("NOT added:\'<20\'");

        ChangeInfo changeWithAddedLines = createChangeInfoFromAddedLines(50);

        List<ChangeInfo> expectedList = Arrays.asList(changeWithAddedLines);
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedLessFilterAppropriateChangesWithMoreLinesWithGivenCase2() throws Exception {
        GerritQuery query = GerritQueryParser.parse("NOT added:\'<20\' OR added:'25'");

        ChangeInfo changeWithAddedLines1 = createChangeInfoFromAddedLines(50);
        ChangeInfo changeWithAddedLines2 = createChangeInfoFromAddedLines(5);
        ChangeInfo changeWithAddedLines3 = createChangeInfoFromAddedLines(10);

        List<ChangeInfo> expectedList = Arrays.asList( changeWithAddedLines1 );
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines1,changeWithAddedLines2,changeWithAddedLines3);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldAddedLessFilterAppropriateChangesWithMoreLinesWithGivenCase3() throws Exception {
        GerritQuery query = GerritQueryParser.parse("NOT ( added:\'<20\' OR added:'25'  )");

        ChangeInfo changeWithAddedLines1 = createChangeInfoFromAddedLines(50);
        ChangeInfo changeWithAddedLines2 = createChangeInfoFromAddedLines(5);
        ChangeInfo changeWithAddedLines3 = createChangeInfoFromAddedLines(10);
        ChangeInfo changeWithAddedLines4 = createChangeInfoFromAddedLines(25);

        List<ChangeInfo> expectedList = Arrays.asList( changeWithAddedLines1 );
        List<ChangeInfo> inputList = Arrays.asList(changeWithAddedLines1,changeWithAddedLines2,changeWithAddedLines3,changeWithAddedLines4);
        assertEquals(expectedList, query.execute(inputList));
    }

    private ChangeInfo createStarredChange(String id, boolean hasStarred) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setStarred(hasStarred);
        return changeInfo;
    }

    @Test
    public void shouldStarredFilterAppropriateChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("is:starred");

        ChangeInfo changeStarred = createStarredChange("1", true);
        ChangeInfo changeNotStarred = createStarredChange("2", false);

        List<ChangeInfo> expectedList = Arrays.asList( changeStarred );
        List<ChangeInfo> inputList = Arrays.asList(changeStarred,changeNotStarred);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldHasStarrFilterAppropriateChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("has:star");

        ChangeInfo changeStarred = createStarredChange("1", true);
        ChangeInfo changeNotStarred = createStarredChange("2", false);

        List<ChangeInfo> expectedList = Arrays.asList( changeStarred );
        List<ChangeInfo> inputList = Arrays.asList(changeStarred,changeNotStarred);
        assertEquals(expectedList, query.execute(inputList));
    }

    private ChangeInfo createDraftedCommentChange(String id, boolean hasDraft) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setHasDraftComments(hasDraft);
        return changeInfo;
    }

    @Test
    public void shouldHasDraftFilterAppropriateChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("has:draft");

        ChangeInfo changeWithDraft = createDraftedCommentChange("1", true);
        ChangeInfo changeWithoutDraft = createDraftedCommentChange("2", false);

        List<ChangeInfo> expectedList = Arrays.asList( changeWithDraft );
        List<ChangeInfo> inputList = Arrays.asList(changeWithDraft,changeWithoutDraft);
        assertEquals(expectedList, query.execute(inputList));
    }

    private ChangeInfo createMergeableChange(String id, boolean isMergeable) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setMergeable(isMergeable);
        return changeInfo;
    }

    @Test
    public void shouldMergeableFilterAppropriateChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("is:mergeable");

        ChangeInfo changeMergeable = createMergeableChange("1", true);
        ChangeInfo changeNotMergeable = createMergeableChange("2", false);

        List<ChangeInfo> expectedList = Arrays.asList( changeMergeable );
        List<ChangeInfo> inputList = Arrays.asList(changeMergeable,changeNotMergeable);
        assertEquals(expectedList, query.execute(inputList));
    }

    private ChangeInfo createReviewedChange(String id, boolean isReviewed) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setReviewed(isReviewed);
        return changeInfo;
    }

    @Test
    public void shouldReviewedFilterAppropriateChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("is:reviewed");

        ChangeInfo changeReviewed = createReviewedChange("1", true);
        ChangeInfo changeNotReviewed = createReviewedChange("2", false);

        List<ChangeInfo> expectedList = Arrays.asList( changeReviewed );
        List<ChangeInfo> inputList = Arrays.asList(changeReviewed,changeNotReviewed);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldStatusReviewedFilterAppropriateChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("status:reviewed");

        ChangeInfo changeReviewed = createReviewedChange("1", true);
        ChangeInfo changeNotReviewed = createReviewedChange("2", false);

        List<ChangeInfo> expectedList = Arrays.asList( changeReviewed );
        List<ChangeInfo> inputList = Arrays.asList(changeReviewed,changeNotReviewed);
        assertEquals(expectedList, query.execute(inputList));
    }

    private ChangeInfo createBrancheChange(String id, String branch) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setBranch(branch);
        return changeInfo;
    }

    @Test
    public void shouldBranchFilterAppropriateChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("branch:'dev'");

        ChangeInfo changeMatchBranch = createBrancheChange("1", "dev");
        ChangeInfo changeNotMatchBranch = createBrancheChange("2", "develop");

        List<ChangeInfo> expectedList = Arrays.asList( changeMatchBranch );
        List<ChangeInfo> inputList = Arrays.asList(changeMatchBranch,changeNotMatchBranch);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldBranchRegexpFilterAppropriateChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("branch:'^dev.*'");

        ChangeInfo changeMatchBranch = createBrancheChange("1", "dev");
        ChangeInfo changeNotMatchBranch = createBrancheChange("2", "develop");

        List<ChangeInfo> expectedList = Arrays.asList( changeMatchBranch ,changeNotMatchBranch);
        List<ChangeInfo> inputList = Arrays.asList(changeMatchBranch,changeNotMatchBranch);
        assertEquals(expectedList, query.execute(inputList));
    }

    private ChangeInfo createProjectChange(String id, String project) {
        ChangeInfo changeInfo = new ChangeInfo(id);
        changeInfo.setProject(project);
        return changeInfo;
    }

    @Test
    public void shouldProjectFilterAppropriateChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("project:'pro'");

        ChangeInfo changeMatchProject = createProjectChange("1", "pro");
        ChangeInfo changeNotMatchProject = createProjectChange("2", "project");

        List<ChangeInfo> expectedList = Arrays.asList( changeMatchProject );
        List<ChangeInfo> inputList = Arrays.asList(changeMatchProject,changeNotMatchProject);
        assertEquals(expectedList, query.execute(inputList));
    }

    @Test
    public void shouldProjectRegexpFilterAppropriateChanges() throws Exception {
        GerritQuery query = GerritQueryParser.parse("project:'^p.[ou].*'");

        ChangeInfo changeMatchProject = createProjectChange("1", "pro");
        ChangeInfo changeNotMatchProject = createProjectChange("2", "pruject");

        List<ChangeInfo> expectedList = Arrays.asList( changeMatchProject ,changeNotMatchProject);
        List<ChangeInfo> inputList = Arrays.asList(changeMatchProject,changeNotMatchProject);
        assertEquals(expectedList, query.execute(inputList));
    }


}










