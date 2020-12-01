
Map pipelineParams = [
        "slackChannel"                          : "data-weave-bot",
        "enableSlackSuccessNotifications"        : true,
        "enableSlackFailedTestsNotifications"    : true,
        "enableAllureTestReportStage"           : false,
        "enableSonarQubeStage"                  : false,
        "enableNexusIqStage"                    : false,
        "mavenSettingsXmlId"                    : "data-weave-maven-settings",
        "devBranchesRegex"                      : "master",
        "enableScheduleTrigger"                 : true,
        "scheduleTriggerCommand"                : "@daily",
        "archiveArtifacts"                      : "data-weave-tita-it/target/Test*.log",
        "projectType"                           : "runtime"
]

runtimeBuild(pipelineParams)
