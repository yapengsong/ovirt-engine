package org.ovirt.engine.core.bll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ovirt.engine.core.common.businessentities.Snapshot;
import org.ovirt.engine.core.common.businessentities.Snapshot.SnapshotStatus;
import org.ovirt.engine.core.common.businessentities.Snapshot.SnapshotType;
import org.ovirt.engine.core.common.queries.GetAllVmSnapshotsByVmIdParameters;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.dao.SnapshotDao;

/**
 * A test case for {@link GetAllVmSnapshotsByVmIdQuery}. This test mocks away all the DAOs, and just tests the flow of
 * the query itself.
 */
public class GetAllVmSnapshotsByVmIdQueryTest
        extends AbstractUserQueryTest<GetAllVmSnapshotsByVmIdParameters,
        GetAllVmSnapshotsByVmIdQuery<GetAllVmSnapshotsByVmIdParameters>> {

    /** The {@link SnapshotDao} mocked for the test */
    private SnapshotDao snapshotDaoMock;

    /** The ID of the VM the disks belong to */
    private Guid vmId;

    /** A snapshot for the test */
    private Snapshot snapshot;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        vmId = Guid.NewGuid();
        snapshot =
                new Snapshot(Guid.NewGuid(), SnapshotStatus.OK, vmId, null, SnapshotType.REGULAR, "", new Date(), "");
        setUpDAOMocks();
    }

    private void setUpDAOMocks() {

        // Mock the DAOs
        DbFacade dbFacadeMock = getDbFacadeMockInstance();

        // Disk Image DAO
        snapshotDaoMock = mock(SnapshotDao.class);
        when(dbFacadeMock.getSnapshotDao()).thenReturn(snapshotDaoMock);
        when(snapshotDaoMock.getForVm(vmId, getUser().getUserId(), getQueryParameters().isFiltered()))
                .thenReturn(Arrays.asList(snapshot));
    }

    @Test
    public void testExecuteQueryCommand() {
        GetAllVmSnapshotsByVmIdParameters params = getQueryParameters();
        when(params.getVmId()).thenReturn(vmId);

        GetAllVmSnapshotsByVmIdQuery<GetAllVmSnapshotsByVmIdParameters> query = getQuery();
        query.executeQueryCommand();

        @SuppressWarnings("unchecked")
        List<Snapshot> snapshots = (List<Snapshot>) query.getQueryReturnValue().getReturnValue();

        // Assert the correct disks are returned
        assertTrue("snapshot should be in the return value", snapshots.contains(snapshot));
        assertEquals("there should be exactly one snapshot returned", 1, snapshots.size());
    }
}
