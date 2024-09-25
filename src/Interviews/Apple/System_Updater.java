package Interviews.Apple;


interface AppleTVUpdater {
    // Method to check for available updates
    boolean checkForUpdates();

    // Method to download the available update
    void downloadUpdate();

    // Method to install the downloaded update
    void installUpdate();

    // Method to get the current software version
    String getCurrentVersion();

    // Method to get the latest software version available
    String getLatestVersion();
}

class AppleTVSystemUpdater implements AppleTVUpdater {
        private String currentVersion = "1.0.0";
        private String latestVersion = "1.1.0";
        private boolean updateAvailable = false;

        @Override
        public boolean checkForUpdates() {
            // Simulate check for updates
            updateAvailable = !currentVersion.equals(latestVersion);
            return updateAvailable;
        }

        @Override
        public void downloadUpdate() {
            if (updateAvailable) {
                System.out.println("Downloading update...");
                // Simulate download logic
            } else {
                System.out.println("No updates available.");
            }
        }

        @Override
        public void installUpdate() {
            if (updateAvailable) {
                System.out.println("Installing update...");
                // Simulate installation logic
                currentVersion = latestVersion;
                updateAvailable = false;
                System.out.println("Update installed successfully to version: " + currentVersion);
            } else {
                System.out.println("No updates to install.");
            }
        }

        @Override
        public String getCurrentVersion() {
            return currentVersion;
        }

        @Override
        public String getLatestVersion() {
            return latestVersion;
        }

        public static void main(String[] args) {
            AppleTVSystemUpdater updater = new AppleTVSystemUpdater();
            System.out.println("Current version: " + updater.getCurrentVersion());

            if (updater.checkForUpdates()) {
                System.out.println("New version available: " + updater.getLatestVersion());
                updater.downloadUpdate();
                updater.installUpdate();
            } else {
                System.out.println("Your Apple TV is up-to-date.");
            }
        }
    }


//Thread safe version
class AppleTVSystemUpdater_thread_safe implements AppleTVUpdater {
    private String currentVersion = "1.0.0";
    private String latestVersion = "1.1.0";
    private boolean updateAvailable = false;

    // Synchronize the method to make it thread-safe
    @Override
    public synchronized boolean checkForUpdates() {
        updateAvailable = !currentVersion.equals(latestVersion);
        if (updateAvailable) {
            System.out.println("Update available. Current version: " + currentVersion + ", Latest version: " + latestVersion);
        } else {
            System.out.println("Your Apple TV is already up to date.");
        }
        return updateAvailable;
    }

    // Synchronize the method to prevent race conditions during download
    @Override
    public synchronized void downloadUpdate() {
        if (updateAvailable) {
            System.out.println("Downloading update...");
            // Simulate download logic
        } else {
            System.out.println("No updates to download.");
        }
    }

    // Synchronize the method to prevent simultaneous installation by multiple threads
    @Override
    public synchronized void installUpdate() {
        if (updateAvailable) {
            System.out.println("Installing update...");
            // Simulate installation logic
            currentVersion = latestVersion;
            updateAvailable = false;
            System.out.println("Update installed successfully to version: " + currentVersion);
        } else {
            System.out.println("No updates to install.");
        }
    }

    @Override
    public synchronized String getCurrentVersion() {
        return currentVersion;
    }

    @Override
    public synchronized String getLatestVersion() {
        return latestVersion;
    }

    public static void main(String[] args) {
        AppleTVSystemUpdater updater = new AppleTVSystemUpdater();

        // Example with multiple threads trying to run updates
        Runnable updaterTask = () -> {
            if (updater.checkForUpdates()) {
                updater.downloadUpdate();
                updater.installUpdate();
            }
        };

        // Create multiple threads simulating the system update being triggered concurrently
        Thread t1 = new Thread(updaterTask);
        Thread t2 = new Thread(updaterTask);

        t1.start();
        t2.start();
    }
}


/**
 * Synchronized Methods:
 * Each method that modifies shared resources (like currentVersion or updateAvailable) is marked
 * with synchronized. This ensures that only one thread at a time can execute these methods, preventing race conditions.
 *
 * Thread Safety:
 * By synchronizing the checkForUpdates, downloadUpdate, and installUpdate methods, we prevent multiple threads from
 * making conflicting updates to shared state.
 */