package practice;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.Candidate;
import model.Cat;
import model.Person;

public class StreamPractice {
    /**
     * Given list of strings where each element contains 1+ numbers:
     * input = {"5,30,100", "0,22,7", ...}
     * return min integer value. One more thing - we're interested in even numbers.
     * If there is no needed data throw RuntimeException with message
     * "Can't get min value from list: < Here is our input 'numbers' > "
     */
    public int findMinEvenNumber(List<String> numbers) {


        return numbers.stream()
                .flatMap(str -> Arrays.stream(str.split(",")))
                .map(String::trim)
                .map(Integer::parseInt)
                .filter(num -> num % 2 == 0)
                .min(Integer::compare)
                .orElseThrow(() -> new RuntimeException(
                        "Can't get min value from list: " + String.join(", ", numbers)));
        /**
         * Given a List of Integer numbers,
         * return the average of all odd numbers from the list or throw NoSuchElementException.
         * But before that subtract 1 from each element on an odd position (having the odd index).
         */
    }

    public Double getOddNumsAverage(List<Integer> numbers) {
        return IntStream.range(0, numbers.size())
                .map(i -> i % 2 != 0 ? numbers.get(i) - 1 : numbers.get(i))
                .filter(num -> num % 2 != 0)
                .average()
                .orElseThrow(() -> new NoSuchElementException("No odd numbers after processing"));

        /**
         * Given a List of `Person` instances (having `name`, `age` and `sex` fields),
         * for example, `Arrays.asList( new Person(«Victor», 16, Sex.MAN),
         * new Person(«Helen», 42, Sex.WOMAN))`,
         * select from the List only men whose age is from `fromAge` to `toAge` inclusively.
         * <p>
         * Example: select men who can be recruited to army (from 18 to 27 years old inclusively).
         */
    }

    public List<Person> selectMenByAge(List<Person> peopleList, int fromAge, int toAge) {
        return peopleList.stream()
                .filter(person -> person.getSex() == Person.Sex.MAN)
                .filter(person -> person.getAge() >= fromAge && person.getAge() <= toAge)
                .collect(Collectors.toList());

        /**
         * Given a List of `Person` instances (having `name`, `age` and `sex` fields),
         * for example, `Arrays.asList( new Person(«Victor», 16, Sex.MAN),
         * new Person(«Helen», 42, Sex.WOMAN))`,
         * select from the List only people whose age is from `fromAge` and to `maleToAge` (for men)
         * or to `femaleToAge` (for women) inclusively.
         * <p>
         * Example: select people of working age
         * (from 18 y.o. and to 60 y.o. for men and to 55 y.o. for women inclusively).
         */
    }

    public List<Person> getWorkablePeople(int fromAge, int femaleToAge,
                                          int maleToAge, List<Person> peopleList) {
        return peopleList.stream()
                .filter(person -> person.getSex() == Person.Sex.MAN
                        ? person.getAge() >= fromAge && person.getAge() <= maleToAge
                        : person.getAge() >= fromAge && person.getAge() <= femaleToAge)
                .collect(Collectors.toList());

        /**
         * Given a List of `Person` instances (having `name`, `age`, `sex` and `cats` fields,
         * and each `Cat` having a `name` and `age`),
         * return the names of all cats whose owners are women from `femaleAge` years old inclusively.
         */
    }

    public List<String> getCatsNames(List<Person> peopleList, int femaleAge) {
        return peopleList.stream()
                .filter(person -> person.getSex()
                        == Person.Sex.WOMAN && person.getAge() >= femaleAge)
                .flatMap(person -> person.getCats().stream())
                .map(Cat::getName)
                .collect(Collectors.toList());
        /**
         * Given a List of `Person` instances (having `name`, `age`, `sex` and `cats` fields,
         * and each `Cat` having a `name` and `age`),
         * return the names of all cats whose owners are women from `femaleAge` years old inclusively.
         */
    }

    public List<String> validateCandidates(List<Candidate> candidates) {
        Predicate<Candidate> validator = new CandidateValidator();
        return candidates.stream()
                .filter(validator)
                .map(Candidate::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
