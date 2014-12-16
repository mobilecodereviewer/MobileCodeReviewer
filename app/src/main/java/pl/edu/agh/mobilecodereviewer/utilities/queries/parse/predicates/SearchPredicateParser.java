package pl.edu.agh.mobilecodereviewer.utilities.queries.parse.predicates;


import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParseException;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.after.AfterSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.age.AgedSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.age.parser.AgeValue;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.age.parser.AgeValueParser;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.before.BeforeSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.branch.BranchSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.change.ChangeSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.draft.HasDraftCommentSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.AddedLinesSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.DeletedLinesSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.DeltaSizeLinesSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.parser.RelationLines;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.relation.IntRelation;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.logic.NotSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.mergeable.MergeableSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.owner.OwnerSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.project.ProjectSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.project.ProjectsSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.starred.StarredSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.status.*;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.utils.StringPatternMatcher;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.ParseUtils;
import pl.edu.agh.mobilecodereviewer.utilities.queries.utils.TimeUtils;

import java.util.Date;

public class SearchPredicateParser {
    public static SearchOperator parseCommand(String predicate) {
        SearchPredicate command = new SearchPredicate(predicate);

        String predicate_base = command.getBase();
        String predicate_options = command.getExtension();
        boolean is_negated = command.isNegated();

        SearchCommandOptionParser searchCommandOptionParser = new SearchCommandOptionParser();
        SearchOperator resultOperator =
                createSearchOperator(predicate_base, predicate_options, searchCommandOptionParser);

        if (is_negated)
            return new NotSearchOperator( resultOperator );
        else return resultOperator;
    }

    private static SearchOperator createSearchOperator(String predicate_base, String predicate_options, SearchCommandOptionParser searchCommandOptionParser) {
        SearchOperator resultOperator;
        switch (predicate_base.toLowerCase()) {
            case "status":
                resultOperator =  searchCommandOptionParser.parseStatusCommand(predicate_options);
                break;
            case "is":
                resultOperator =  searchCommandOptionParser.parseIdentityCommand(predicate_options);
                break;
            case "age":
                resultOperator =  searchCommandOptionParser.parseAgeCommand(predicate_options);
                break;
            case "before":
                resultOperator =  searchCommandOptionParser.parseBeforeCommand(predicate_options);
                break;
            case "until":
                resultOperator =  searchCommandOptionParser.parseBeforeCommand(predicate_options);
                break;
            case "after":
                resultOperator =  searchCommandOptionParser.parseAfterCommand(predicate_options);
                break;
            case "since":
                resultOperator =  searchCommandOptionParser.parseAfterCommand(predicate_options);
                break;
            case "change":
                resultOperator =  searchCommandOptionParser.parseChangeCommand(predicate_options);
                break;
            case "owner":
                resultOperator =  searchCommandOptionParser.parseOwnerCommand(predicate_options);
                break;
            case "o":
                resultOperator =  searchCommandOptionParser.parseOwnerCommand(predicate_options);
                break;
            case "p":
            case "project":
                resultOperator = searchCommandOptionParser.parseProjectCommand(predicate_options);
                break;
            case "projects":
                resultOperator =  searchCommandOptionParser.parseProjectsCommand(predicate_options);
                break;
            case "added":
                resultOperator =  searchCommandOptionParser.parseAddedCommand(predicate_options);
                break;
            case "deleted":
                resultOperator =  searchCommandOptionParser.parseDeletedCommand(predicate_options);
                break;
            case "delta/size":
                resultOperator =  searchCommandOptionParser.parseDeltaSizeCommand(predicate_options);
                break;
            case "has":
                resultOperator =  searchCommandOptionParser.parseHasCommand(predicate_options);
                break;
            case "branch":
                resultOperator = searchCommandOptionParser.parseBranchCommand(predicate_options);
                break;
            default:
                throw new GerritQueryParseException("Unrecognized query parameter : \"" + predicate_base + "\"");
        }
        return resultOperator;
    }

    public static class SearchCommandOptionParser {

        public SearchOperator parseStatusCommand(String statusQuery) {
            switch (statusQuery.toLowerCase() ) {
                case "all":
                    return new AllStatusSearchOperator();
                case "open":
                    return new OpenStatusSearchOperator() ;
                case "merged":
                    return new MergedStatusSearchOperator() ;
                case "abandoned":
                    return new AbandonedStatusSearchOperator() ;
                case "submitted":
                    return new SubmittedStatusSearchOperator() ;
                case "closed":
                    return new ClosedStatusSearchOperator() ;
                case "reviewed":
                    return new ReviewedStatusSearchOperator();

                default:
                    throw new GerritQueryParseException("Unrecognized option for status: \"" + statusQuery + "\"");
            }

        }

        public SearchOperator parseIdentityCommand(String identityQuery) {
            switch (identityQuery.toLowerCase()) {
                case "open":
                    return parseStatusCommand(identityQuery);
                case "draft":
                    return new DraftedStatusSearchOperator() ;
                case "merged":
                    return new MergedStatusSearchOperator() ;
                case "closed":
                    return new ClosedStatusSearchOperator() ;
                case "abandoned":
                    return new AbandonedStatusSearchOperator() ;
                case "submitted":
                    return new SubmittedStatusSearchOperator() ;
                case "starred":
                    return new StarredSearchOperator();
                case "mergeable":
                    return new MergeableSearchOperator();
                case "reviewed":
                    return parseStatusCommand(identityQuery);

                default:
                    throw new GerritQueryParseException("Unrecognized option for \"is\": \"" + identityQuery + "\"");
            }
        }

        public SearchOperator parseHasCommand(String hasQuery) {
            switch (hasQuery.toLowerCase()) {
                case "draft":
                    return new HasDraftCommentSearchOperator();
                case "star":
                    return parseIdentityCommand("starred");

                default:
                    throw new GerritQueryParseException("Unrecognized option for \"has\": \"" + hasQuery + "\"");
            }
        }

        public SearchOperator parseAgeCommand(String age) {
            AgeValue ageValue = AgeValueParser.parse(age);
            return new AgedSearchOperator(ageValue ) ;
        }


        public SearchOperator parseBeforeCommand(String value) {
            Date parsedDate = TimeUtils.parseDate(value);
            return new BeforeSearchOperator(parsedDate) ;
        }

        public SearchOperator parseAfterCommand(String value) {
            Date parsedDate = TimeUtils.parseDate(value);
            return new AfterSearchOperator(parsedDate) ;
        }


        public SearchOperator parseChangeCommand(String value) {
            String valWithoutParantheses = ParseUtils.parseValueFromQuotingDelimeters(value);
            return new ChangeSearchOperator(valWithoutParantheses) ;
        }

        public SearchOperator parseOwnerCommand(String value) {
            String valWithoutParantheses = ParseUtils.parseValueFromQuotingDelimeters(value);
            return new OwnerSearchOperator(valWithoutParantheses) ;
        }

        public SearchOperator parseProjectsCommand(String value) {
            String valWithoutParanthese = ParseUtils.parseValueFromQuotingDelimeters(value);
            return new ProjectsSearchOperator(valWithoutParanthese) ;
        }

        public SearchOperator parseAddedCommand(String value) {
            String valWithoutParantheses = ParseUtils.parseValueFromQuotingDelimeters(value);
            IntRelation rel = RelationLines.parse(valWithoutParantheses);
            return new AddedLinesSearchOperator(rel) ;
        }


        public SearchOperator parseDeletedCommand(String value) {
            String valWithoutParantheses = ParseUtils.parseValueFromQuotingDelimeters(value);
            IntRelation rel = RelationLines.parse(valWithoutParantheses);
            return new DeletedLinesSearchOperator(rel) ;
        }

        public SearchOperator parseDeltaSizeCommand(String value) {
            String valWithoutParantheses = ParseUtils.parseValueFromQuotingDelimeters(value);
            IntRelation rel = RelationLines.parse(valWithoutParantheses);
            return new DeltaSizeLinesSearchOperator(rel);
        }

        public SearchOperator parseBranchCommand(String value) {
            String valWithoutParantheses = ParseUtils.parseValueFromQuotingDelimeters(value);
            StringPatternMatcher patternMatcher = new StringPatternMatcher(valWithoutParantheses);
            return new BranchSearchOperator(patternMatcher);
        }

        public SearchOperator parseProjectCommand(String value) {
            String valWithoutParantheses = ParseUtils.parseValueFromQuotingDelimeters(value);
            StringPatternMatcher patternMatcher = new StringPatternMatcher(valWithoutParantheses);
            return new ProjectSearchOperator(patternMatcher);
        }
    }
}
