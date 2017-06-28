package wizard.models;

/**
 * Created by fpr on 28.06.2017.
 */
public class Configuration {

    private int id;
    private String name;
    private int userId;
    private boolean scenario;
    private boolean outcome;
    private boolean competence;
    private boolean content;
    private boolean materials;
    private boolean comments;
    private boolean techniques;
    private boolean achievements;

    public Configuration() {
        scenario = false;
        outcome = false;
        competence = false;
        content = false;
        materials = false;
        comments = false;
        techniques = false;
        achievements = false;
    }

    public Configuration(String name, int userId, boolean scenario, boolean outcome, boolean competence, boolean content, boolean materials, boolean comments, boolean techniques, boolean achievements) {
        this.name = name;
        this.userId = userId;
        this.scenario = scenario;
        this.outcome = outcome;
        this.competence = competence;
        this.content = content;
        this.materials = materials;
        this.comments = comments;
        this.techniques = techniques;
        this.achievements = achievements;
    }

    public Configuration(int id, String name, int userId, boolean scenario, boolean outcome, boolean competence, boolean content, boolean materials, boolean comments, boolean techniques, boolean achievements) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.scenario = scenario;
        this.outcome = outcome;
        this.competence = competence;
        this.content = content;
        this.materials = materials;
        this.comments = comments;
        this.techniques = techniques;
        this.achievements = achievements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isScenario() {
        return scenario;
    }

    public void setScenario(boolean scenario) {
        this.scenario = scenario;
    }

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }

    public boolean isCompetence() {
        return competence;
    }

    public void setCompetence(boolean competence) {
        this.competence = competence;
    }

    public boolean isContent() {
        return content;
    }

    public void setContent(boolean content) {
        this.content = content;
    }

    public boolean isMaterials() {
        return materials;
    }

    public void setMaterials(boolean materials) {
        this.materials = materials;
    }

    public boolean isComments() {
        return comments;
    }

    public void setComments(boolean comments) {
        this.comments = comments;
    }

    public boolean isTechniques() {
        return techniques;
    }

    public void setTechniques(boolean techniques) {
        this.techniques = techniques;
    }

    public boolean isAchievements() {
        return achievements;
    }

    public void setAchievements(boolean achievements) {
        this.achievements = achievements;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", scenario=" + scenario +
                ", outcome=" + outcome +
                ", competence=" + competence +
                ", content=" + content +
                ", materials=" + materials +
                ", comments=" + comments +
                ", techniques=" + techniques +
                ", achievements=" + achievements +
                '}';
    }
}
