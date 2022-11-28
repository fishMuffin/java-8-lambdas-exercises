package com.insightfullogic.java8.practice;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;
import com.insightfullogic.java8.examples.chapter1.Track;
import com.insightfullogic.java8.examples.chapter5.CollectorExamples;
import org.junit.Test;

import java.awt.event.ActionListener;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class YcwTest {
    @Test
    public void test1() {
        Runnable noArguments = () -> System.out.println("Hello World");

        ActionListener oneArgument = event -> System.out.println("button clicked");

        Runnable multiStatement = () -> {
            System.out.print("Hello");
            System.out.println(" World");
        };

        BinaryOperator<Long> add = (x, y) -> x + y;

        BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;
    }

    @Test
    public void testPredicate() {
        Predicate<Integer> greaterThan5 = x -> x > 5;
        System.out.println(greaterThan5.test(6));
        BinaryOperator<Long> add = (x, y) -> x + y;
        System.out.println(add.apply(1L, 2L));
    }

    @Test
    public void testStream() {
        List<String> collected = Stream.of("a", "b", "c")
                .collect(toList());

        assertEquals(asList("a", "b", "c"), collected);
    }

    @Test
    public void testFlatMap() {
        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(numbers -> numbers.stream())
                .collect(toList());

        assertEquals(asList(1, 2, 3, 4), together);
    }

    @Test
    public void testComparator() {
        List<Track> tracks = asList(new Track("Bakai", 524),
                new Track("Violets for Your Furs", 378),
                new Track("Time Was", 451));

        Track shortestTrack = tracks.stream()
                .min(Comparator.comparing(track -> track.getLength()))
                .get();

        assertEquals(tracks.get(1), shortestTrack);
    }

    @Test
    public void testReducer() {
        int count = Stream.of(1, 2, 3)
                .reduce(0, (acc, element) -> acc + element);

        assertEquals(6, count);
    }

    @Test
    public void testStream1() {
        List<Integer> list = new ArrayList<>();
        Arrays.asList(1, 2, 3).forEach(s -> list.add(s));
        list.forEach(s -> System.out.println(s));

        List<Student> students = asList(new Student(1, "Alex", "female", Arrays.asList(new Class1(1, "Class1"))), new Student(2, "Alex2", "male", Arrays.asList(new Class1(2, "Class2"))), new Student(3, "Alex3", "male", Arrays.asList(new Class1(3, "Class3"))));
//        List<String> collect = students.stream()
//                .map(student -> student.getClass1List())
//                .filter(class1 -> class1.getId() > 1)
//                .map(class1 -> class1.getName())
//                .collect(toList());
        List<String> collect1 = students.stream()
                .flatMap(student -> student.getClass1List())
                .filter(class1 -> class1.getId() > 1)
                .map(class1 -> class1.getName())
                .collect(toList());
        System.out.println(collect1);
    }

    private String str1;

    @Test
    public void testVariableChange() {
        String str;
//        Arrays.asList(1).stream().forEach(s -> str = "" + 1);
        Arrays.asList(1, 2, 3).stream().forEach(s -> str1 = "" + 1);
        System.out.println(str1);
    }

    @Test
    public void testExercises() {
//        Common Stream operations. Implement the following:
//
//        A function that adds up numbers, i.e., int addUp(Stream<Integer> numbers)
//        A function that takes in artists and returns a list of strings with their names and places of origin
//        A function that takes in albums and returns a list of albums with at most three tracks

        List<Artist> artists = SampleData.getThreeArtists();
        System.out.println(addUp(asList(1, 2, 3).stream()));
        System.out.println(namesAndOriginsFromArtists(artists));
        getAlbumsWithLessThan3Tracks(Arrays.asList(SampleData.manyTrackAlbum, SampleData.aLoveSupreme)).forEach(s -> System.out.println(s));

        int totalMembers = 0;
        for (Artist artist : artists) {
            Stream<Artist> members = artist.getMembers();
            totalMembers += members.count();
        }


        Long reduce = artists.stream().map(artist -> artist.getMembers().count())
                .reduce(0L, Long::sum);
        System.out.println(reduce);


    }


    List<Album> getAlbumsWithLessThan3Tracks(List<Album> albums) {
        return albums.stream()
                .filter(album -> album.getTracks().count() <= 3)
                .collect(Collectors.toList());

    }

    List<String> namesAndOriginsFromArtists(List<Artist> artists) {
        return artists.stream()
                .map(artist -> artist.getName() + " from " + artist.getNationality())
                .collect(Collectors.toList());
    }

    int addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, (acc, ele) -> acc + ele);
    }

    @Test
    public void testBoxing() {
        Album album = SampleData.manyTrackAlbum;
        IntSummaryStatistics trackLengthStats
                = album.getTracks()
                .mapToInt(track -> track.getLength())
                .summaryStatistics();

        System.out.printf("Max: %d, Min: %d, Ave: %f, Sum: %d",
                trackLengthStats.getMax(),
                trackLengthStats.getMin(),
                trackLengthStats.getAverage(),
                trackLengthStats.getSum());

    }

    @Test
    public void testObjects() {
        System.out.println(Objects.isNull(null));
        System.out.println(Objects.isNull(1));
        System.out.println(Objects.hash(1));
        System.out.println(Objects.deepEquals("123", "123"));
//        System.out.println(Objects.requireNonNull(null).toString());
//        Objects.requireNonNull(null,"不能为空！");
        Objects.requireNonNull(null, new Supplier<String>() {
            @Override
            public String get() {
                return "hah";
            }
        });

    }

    @Test
    public void testPartitionBy() {
        CollectorExamples collectorExamples = new CollectorExamples();
//        Map<Boolean, List<Artist>> booleanListMap = new CollectorExamples().bandsAndSoloRef(SampleData.getThreeArtists().stream());
//        System.out.println(booleanListMap);
//        Map<Artist, List<Album>> artistListMap = collectorExamples.albumsByArtist(SampleData.albums);
//        Map<Artist, Long> artistLongMap = collectorExamples.numberOfAlbums(SampleData.albums);
        Map<Artist, List<String>> artistListMap = collectorExamples.nameOfAlbums(SampleData.albums);
        System.out.println(artistListMap);
    }
//    <U> U reduce(U var1, BiFunction<U, ? super T, U> var2, BinaryOperator<U> var3);
    @Test
    public void testUseReduce() {
        Stream<Artist> artists = SampleData.threeArtists();
        StringBuilder reduced =
                artists.map(Artist::getName)
                        .reduce(new StringBuilder(), (builder, name) -> {
                            if (builder.length() > 0)
                                builder.append(", ");

                            builder.append(name);
                            return builder;
                        }, (left, right) -> left.append(right));
//                        }, (left, right) -> left.append(right));

        reduced.insert(0, "[");
        reduced.append("]");
        String result = reduced.toString();
        System.out.println(result);
    }
}
