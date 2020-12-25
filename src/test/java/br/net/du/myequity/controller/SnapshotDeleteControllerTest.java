package br.net.du.myequity.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class SnapshotDeleteControllerTest extends PostControllerTestBase {

    public SnapshotDeleteControllerTest() {
        super("/snapshot/delete/42");
    }
}
