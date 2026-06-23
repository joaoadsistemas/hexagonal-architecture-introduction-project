package com.joao.hexagonal_architecture_introduction_project.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.joao.hexagonal_architecture_introduction_project")
public class NamingConventionTest {


    @ArchTest
    public static final ArchRule consumer_reside_only_consumer_package = classes()
            .that()
            .haveNameMatching(".*Consumer")
            .should()
            .resideInAPackage("..adapters.in.consumer")
            .as("Consumer reside only in consumer package");

    @ArchTest
    public static final ArchRule mapper_reside_only_mapper_package = classes()
            .that()
            .haveNameMatching(".*Mapper")
            .should()
            .resideInAnyPackage("..adapters.in.consumer.mapper", "..adapters.in.controller.mapper", "..adapters.out.client.mapper",
                    "..adapters.out.repository.mapper")
            .as("Mapper reside only in mapper package");

    @ArchTest
    public static final ArchRule message_reside_only_message_package = classes()
            .that()
            .haveNameMatching(".*Message")
            .should()
            .resideInAPackage("..adapters.in.consumer.message")
            .as("Message reside only in message package");

    @ArchTest
    public static final ArchRule controller_reside_only_controller_package = classes()
            .that()
            .haveNameMatching(".*Controller")
            .should()
            .resideInAPackage("..adapters.in.controller")
            .as("Controller reside only in controller package");

    @ArchTest
    public static final ArchRule handler_reside_only_handler_package = classes()
            .that()
            .haveNameMatching(".*Handler")
            .should()
            .resideInAPackage("..adapters.in.controller.handler")
            .as("Handler reside only in handler package");

    @ArchTest
    public static final ArchRule request_reside_only_request_package = classes()
            .that()
            .haveNameMatching(".*Request")
            .should()
            .resideInAPackage("..adapters.in.controller.request")
            .as("Request reside only in request package");

    @ArchTest
    public static final ArchRule response_reside_only_response_package = classes()
            .that()
            .haveNameMatching(".*Response")
            .should()
            .resideInAnyPackage("..adapters.in.controller.response", "..adapters.out.client.response")
            .as("Response reside only in response package");

    @ArchTest
    public static final ArchRule client_reside_only_client_package = classes()
            .that()
            .haveNameMatching(".*Client")
            .should()
            .resideInAPackage("..adapters.out.client")
            .as("Client reside only in client package");

    @ArchTest
    public static final ArchRule entity_reside_only_entity_package = classes()
            .that()
            .haveNameMatching(".*Entity")
            .should()
            .resideInAPackage("..adapters.out.repository.entity")
            .as("Entity reside only in entity package");

    @ArchTest
    public static final ArchRule repository_reside_only_repository_package = classes()
            .that()
            .haveNameMatching(".*Repository")
            .should()
            .resideInAPackage("..adapters.out.repository")
            .as("Repository reside only in repository package");

    @ArchTest
    public static final ArchRule adapter_reside_only_adapter_package = classes()
            .that()
            .haveNameMatching(".*Adapter")
            .should()
            .resideInAPackage("..adapters.out")
            .as("Adapter reside only in adapter package");

    @ArchTest
    public static final ArchRule exception_reside_only_exception_package = classes()
            .that()
            .haveNameMatching(".*Exception")
            .should()
            .resideInAnyPackage("..application.core.useCase.exception", "..application.ports.in.exception")
            .as("Exception reside only in exception package");

    @ArchTest
    public static final ArchRule useCase_reside_only_useCase_package = classes()
            .that()
            .haveNameMatching(".*UseCase")
            .should()
            .resideInAPackage("..application.core.useCase")
            .as("UseCase reside only in useCase package");

    @ArchTest
    public static final ArchRule inputPort_reside_only_inputPort_package = classes()
            .that()
            .haveNameMatching(".*InputPort")
            .should()
            .resideInAPackage("..application.ports.in")
            .as("InputPort reside only in inputPort package");

    @ArchTest
    public static final ArchRule outputPort_reside_only_outputPort_package = classes()
            .that()
            .haveNameMatching(".*OutputPort")
            .should()
            .resideInAPackage("..application.ports.out")
            .as("OutputPort reside only in outputPort package");

    @ArchTest
    public static final ArchRule config_reside_only_config_package = classes()
            .that()
            .haveNameMatching(".*Config")
            .should()
            .resideInAPackage("..config")
            .as("Config reside only in config package");



    // suffixes


    @ArchTest
    public static final ArchRule should_be_suffixed_consumer = classes()
            .that()
            .resideInAPackage("..consumer")
            .should()
            .haveSimpleNameEndingWith("Consumer")
            .as("Should be suffixed with Consumer");

    @ArchTest
    public static final ArchRule should_be_suffixed_mapper = classes()
            .that()
            .resideInAPackage("..mapper")
            .should()
            .haveSimpleNameEndingWith("Mapper")
            .orShould()
            .haveSimpleNameEndingWith("MapperImpl")
            .as("Should be suffixed with Mapper");

    @ArchTest
    public static final ArchRule should_be_suffixed_message = classes()
            .that()
            .resideInAPackage("..message")
            .should()
            .haveSimpleNameEndingWith("Message")
            .as("Should be suffixed with Message");

    @ArchTest
    public static final ArchRule should_be_suffixed_request = classes()
            .that()
            .resideInAPackage("..request")
            .should()
            .haveSimpleNameEndingWith("Request")
            .as("Should be suffixed with Request");

    @ArchTest
    public static final ArchRule should_be_suffixed_response = classes()
            .that()
            .resideInAPackage("..response")
            .should()
            .haveSimpleNameEndingWith("Response")
            .as("Should be suffixed with Response");

    @ArchTest
    public static final ArchRule should_be_suffixed_controller = classes()
            .that()
            .resideInAPackage("..controller")
            .should()
            .haveSimpleNameEndingWith("Controller")
            .as("Should be suffixed with Controller");

    @ArchTest
    public static final ArchRule should_be_suffixed_client = classes()
            .that()
            .resideInAPackage("..client")
            .should()
            .haveSimpleNameEndingWith("Client")
            .as("Should be suffixed with Client");

    @ArchTest
    public static final ArchRule should_be_suffixed_entity = classes()
            .that()
            .resideInAPackage("..entity")
            .should()
            .haveSimpleNameEndingWith("Entity")
            .as("Should be suffixed with Entity");

    @ArchTest
    public static final ArchRule should_be_suffixed_repository = classes()
            .that()
            .resideInAPackage("..repository")
            .should()
            .haveSimpleNameEndingWith("Repository")
            .as("Should be suffixed with Repository");

    @ArchTest
    public static final ArchRule should_be_suffixed_adapter = classes()
            .that()
            .resideInAPackage("..adapters.out")
            .should()
            .haveSimpleNameEndingWith("Adapter")
            .as("Should be suffixed with Adapter");

    @ArchTest
    public static final ArchRule should_be_suffixed_exception = classes()
            .that()
            .resideInAPackage("..exception")
            .should()
            .haveSimpleNameEndingWith("Exception")
            .as("Should be suffixed with Exception");

    @ArchTest
    public static final ArchRule should_be_suffixed_useCase = classes()
            .that()
            .resideInAPackage("..useCase")
            .should()
            .haveSimpleNameEndingWith("UseCase")
            .as("Should be suffixed with UseCase");

    @ArchTest
    public static final ArchRule should_be_suffixed_inputPort = classes()
            .that()
            .resideInAPackage("..ports.in")
            .should()
            .haveSimpleNameEndingWith("InputPort")
            .as("Should be suffixed with InputPort");

    @ArchTest
    public static final ArchRule should_be_suffixed_outputPort= classes()
            .that()
            .resideInAPackage("..ports.out")
            .should()
            .haveSimpleNameEndingWith("OutputPort")
            .as("Should be suffixed with OutputPort");

    @ArchTest
    public static final ArchRule should_be_suffixed_config = classes()
            .that()
            .resideInAPackage("..config")
            .should()
            .haveSimpleNameEndingWith("Config")
            .as("Should be suffixed with Config");
}
