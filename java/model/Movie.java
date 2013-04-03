package model;

public class Movie {

	private int movieId;
	private String voteDistribution;
	private int numberOfVotes;
	private float rating;

	public String getVoteDistribution() {
		return voteDistribution;
	}
	public void setVoteDistribution(String voteDistribution) {
		this.voteDistribution = voteDistribution;
	}
	public int getNumberOfVotes() {
		return numberOfVotes;
	}
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getArffStructuredData() {
		String arffStructure = new String();
		String voteDistributionAsCSV = getVoteDistributionInCSV();
		arffStructure = arffStructure + voteDistributionAsCSV;
		arffStructure = arffStructure + "," + getNumberOfVotes();
		arffStructure = arffStructure + "," + getRating();
		return arffStructure;
	}

	public String getVoteDistributionInCSV() {
		String csv = new String();
		String voteDistribution = getVoteDistribution();
		if (voteDistribution != null) {
			for (int i = 0; i < voteDistribution.length(); i++) {
				Character val = voteDistribution.charAt(i);
				int toAppend = 0;
				if (val == '.') {
					toAppend = 0;
				} else if (val == '*') {
					toAppend = 11;
				} else {
					try {
						toAppend = Integer.parseInt(val.toString());
						toAppend = toAppend + 1;
					} catch (NumberFormatException e) {
						System.out.println("Error in printing vote distribution for movie" + getMovieId());
					}
				}
				csv = csv + toAppend + ",";
			}
			csv = csv.substring(0, csv.length() - 1);
		}
		return csv;
	}
}
