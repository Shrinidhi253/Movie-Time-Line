package movieTimeLine;

// Combines movie clips to make 1 whole movie

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class movie {
    String movieName;
    ArrayList<movieClip> movieClips;
    int totalSecElapsed = 0;
    timeStampSpecifier specifier;

    public movie(String movieName, timeStampSpecifier specifier) {
        this.movieName = movieName;
        this.specifier = specifier;
        this.movieClips = new ArrayList<>();
    }

    public movie setName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public movie setTimeStampSpecifier(timeStampSpecifier specifier) {
        this.specifier = specifier;
        return this;
    }

    public void addMovieClip(movieClip clip) {
        // format of the time stamp
        clip.setTimeStampSpecifier(specifier);

        // the total time elapsed will be the offset of a clip
        clip.setOffset(totalSecElapsed);

        // the totalSecElapsed should be updated with the duration of the current clip
        totalSecElapsed += clip.getTotalDurationSec();
        movieClips.add(clip);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("MOVIE: %s\n".formatted(movieName));
        res.append("TIME STAMP SPECIFIER: %s\n".formatted(specifier));

        for (movieClip clip : movieClips) {
            res.append(clip.toString()).append("\n");
        }
        return res.toString();
    }

    public void saveToFile(String fileName) {
        File textFile = new File(fileName);
        try (FileWriter writer = new FileWriter(textFile)) {
            writer.write(toString());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        File clipsFile = new File("src/movieTimeLine/all_clips.txt");
        try (Scanner scanner = new Scanner(clipsFile);){
            movie movie = new movie("Full movie", timeStampSpecifier.START_TIME);
            while (scanner.hasNextLine()) {
                movieClip clip = movieClip.parseString(scanner.nextLine());
                movie.addMovieClip(clip);
            }

            movie.saveToFile("src/movieTimeLine/Joined_Movie.txt");
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
