public class FinalStage extends Stage
{

    @Override
    protected void busy(double currentTime) {

    }

    @Override
    protected TimeEvent waiting(double currentTime, double processingTime) {
        return null;
    }

    @Override
    protected void blocked() {

    }

    @Override
    protected void starved() {

    }
}
