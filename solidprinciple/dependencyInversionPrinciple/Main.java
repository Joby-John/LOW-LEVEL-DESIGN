package solidprinciple.dependencyInversionPrinciple;

/**
 * Strategy interface for different recommendation algorithms
 */
interface RecommendationStrategy {
    void recommend();
}

class RecentRecommendation implements RecommendationStrategy {
    @Override
    public void recommend() {
        System.out.println("Recommending based on recent activity");
    }
}

class GenreRecommendation implements RecommendationStrategy {
    @Override
    public void recommend() {
        System.out.println("Recommending based on genre preferences");
    }
}

class TrendingRecommendation implements RecommendationStrategy {
    @Override
    public void recommend() {
        System.out.println("Recommending trending items");
    }
}

class RecommendationEngine {
    private final RecommendationStrategy recommendationStrategy;

    public RecommendationEngine(RecommendationStrategy recommendationStrategy) {
        this.recommendationStrategy = recommendationStrategy;
    }

    public void recommend() {
        recommendationStrategy.recommend();
    }
}

/**
 * Main class demonstrating Dependency Inversion Principle
 * High-level modules should not depend on low-level modules.
 * Both should depend on abstractions.
 */
public class Main {
    public static void main(String[] args) {
        // Using strategy directly
        RecommendationStrategy trendingStrategy = new TrendingRecommendation();
        trendingStrategy.recommend();

        // Using recommendation engine
        RecommendationEngine engine = new RecommendationEngine(new RecentRecommendation());
        engine.recommend();

        // Easy to switch strategies
        engine = new RecommendationEngine(new GenreRecommendation());
        engine.recommend();
    }
}

// #) abstraction should not depend on details
// none of the abstractions are aware of the details
// like interface are not aware what is happeing inside each recomend , they
// just enforce it

// #) details should depend on abstarction
// like its mandatory that we implement the interfaces(abstarction in this case)
