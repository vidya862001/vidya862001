package com.overstock.partnergateway.warehouse.coordination.integration;

import static com.overstock.partnergateway.core.purchaseorder.api.PurchaseOrder.Status.ACCEPTED;
import static com.overstock.partnergateway.core.purchaseorder.api.PurchaseOrder.Status.REJECTED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.overstock.framework.Bordello;
import com.overstock.model.Code;
import com.overstock.model.Id;
import com.overstock.partnergateway.api.InboundShipmentLineResponseType;
import com.overstock.partnergateway.api.InboundShipmentLineStatusType;
import com.overstock.partnergateway.api.InboundShipmentLineType;
import com.overstock.partnergateway.api.InboundShipmentMessage;
import com.overstock.partnergateway.api.InboundShipmentMessageResponse;
import com.overstock.partnergateway.api.InboundShipmentMessageStatusType;
import com.overstock.partnergateway.api.InboundShipmentResponseType;
import com.overstock.partnergateway.api.InboundShipmentStatusType;
import com.overstock.partnergateway.api.InboundShipmentType;
import com.overstock.partnergateway.api.ProcessedInboundShipmentLineType;
import com.overstock.partnergateway.api.ProcessedInboundShipmentMessage;
import com.overstock.partnergateway.api.ProcessedInboundShipmentType;
import com.overstock.partnergateway.api.ProductUnitOfMeasureType;
import com.overstock.partnergateway.api.ShipmentStatusType;
import com.overstock.partnergateway.core.client.GatewayException;
import com.overstock.partnergateway.core.client.GenericClient;
import com.overstock.partnergateway.core.client.Result;
import com.overstock.partnergateway.core.common.codes.SalesChannelWarehouseCode;
import com.overstock.partnergateway.core.common.codes.WarehouseName;
import com.overstock.partnergateway.core.common.ids.SalesChannelId;
import com.overstock.partnergateway.core.common.ids.SalesChannelWarehouseLinkId;
import com.overstock.partnergateway.core.common.ids.SupplierId;
import com.overstock.partnergateway.core.partner.api.Address;
import com.overstock.partnergateway.core.purchaseorder.api.PurchaseOrder.Status;
import com.overstock.partnergateway.core.search.FilterExpression.LogicalOperator;
import com.overstock.partnergateway.core.search.MultiValueFilter;
import com.overstock.partnergateway.core.search.SingleFilter;
import com.overstock.partnergateway.core.search.Sort;
import com.overstock.partnergateway.core.warehouse.api.SalesChannelWarehouse;
import com.overstock.partnergateway.core.warehouse.api.Warehouse;
import com.overstock.partnergateway.core.warehouse.api.Warehouse.WarehouseType;
import com.overstock.partnergateway.core.warehouse.api.WarehouseList;
import com.overstock.partnergateway.warehouse.common.InboundShipmentQuery;
import com.overstock.partnergateway.warehouse.common.WarehouseCriteria;
import com.overstock.partnergateway.warehouse.coordination.client.WarehouseClient;
import com.overstock.partnergateway.warehouse.coordination.client.WarehouseClient.Impl.MimeType;
import com.overstock.partnergateway.warehouse.coordination.common.InboundShipmentCriteria;
import com.overstock.partnergateway.warehouse.coordination.common.InboundShipmentCriteria.Field;
import com.overstock.partnergateway.warehouse.coordination.common.InboundShipmentCriteria.OrderBy;
import com.overstock.partnergateway.warehouse.coordination.common.dto.PurchaseOrderClientResult;
import com.overstock.partnergateway.warehouse.coordination.common.utils.DataTypeUtils;
import com.sun.jersey.api.client.ClientResponse;

/**
 * End to end test through the client.
 */
public class InboundShipmentIntegrationTest {

  private static WarehouseClient CLIENT;

  private Id<SupplierId> supplierId = new Id<>(1);

  @BeforeClass
  public static void setUp() {
    Bordello.reset();
    CLIENT = Bordello.get(WarehouseClient.class);
  }

  @Test
  public void testHealthCheck() {
    assertTrue(CLIENT.isHealthy());
  }

  @Test
  public void getInboundShipmentBySupplierIdAndNumber() throws Exception {
    String inboundShipmentNumber = "P130800022-E";
    Result<ProcessedInboundShipmentMessage> result = CLIENT.getInboundShipment(supplierId, inboundShipmentNumber);
    assertEquals(200, result.getStatus());
    ProcessedInboundShipmentMessage inboundShipments = result.getResult();
    assertEquals(1, inboundShipments.getProcessedInboundShipments().size());
    ProcessedInboundShipmentType inboundShipment = inboundShipments.getProcessedInboundShipments().get(0);
    assertEquals(inboundShipmentNumber, inboundShipment.getInboundShipmentNumber());
    assertEquals(1, inboundShipment.getProcessedInboundShipmentLines().size());
    ProcessedInboundShipmentLineType line = inboundShipment.getProcessedInboundShipmentLines().get(0);
    assertEquals(15, line.getProcessedInboundShipmentLineReceipts().size());
  }

  @Test
  public void getInboundShipmentBySupplierIdAndNumberForCsv() throws Exception {
    String inboundShipmentNumber = "P130800022-E";
    ClientResponse response = CLIENT.getInboundShipment(supplierId, inboundShipmentNumber, MimeType.CSV);
    assertEquals(200, response.getStatus());
    String csv = IOUtils.toString(response.getEntityInputStream());
    assertTrue(csv.contains(inboundShipmentNumber));
    // TODO: validate more information in csv
  }

  @Test(expected = GatewayException.DoesNotExist.class)
  public void getInboundShipmentBySupplierIdAndNumber404() throws Exception {
    String inboundShipmentNumber = "P130800022-E1";
    CLIENT.getInboundShipment(supplierId, inboundShipmentNumber);
  }

  @Test
  public void getInboundShipmentBySupplierIdAndShipmentId() throws Exception {
    long id = 543;
    Result<ProcessedInboundShipmentMessage> result = CLIENT.getInboundShipment(supplierId, id);
    assertEquals(200, result.getStatus());
    ProcessedInboundShipmentMessage inboundShipments = result.getResult();
    assertEquals(1, inboundShipments.getProcessedInboundShipments().size());
    ProcessedInboundShipmentType inboundShipment = inboundShipments.getProcessedInboundShipments().get(0);
    assertEquals("P130800022-E", inboundShipment.getInboundShipmentNumber());
    assertEquals(1, inboundShipment.getProcessedInboundShipmentLines().size());
    ProcessedInboundShipmentLineType line = inboundShipment.getProcessedInboundShipmentLines().get(0);
    assertEquals(15, line.getProcessedInboundShipmentLineReceipts().size());
  }

  @Test
  public void getInboundShipmentBySupplierIdAndShipmentIdForCsv() throws Exception {
    long id = 543;
    ClientResponse response = CLIENT.getInboundShipment(supplierId, id, MimeType.CSV);
    assertEquals(200, response.getStatus());
    String csv = IOUtils.toString(response.getEntityInputStream());
    assertTrue(csv.contains("543"));
    assertTrue(csv.contains("P130800022-E"));
    // TODO: validate more information in csv
  }

  @Test(expected = GatewayException.DoesNotExist.class)
  public void getInboundShipmentBySupplierIdAndShipmentId404() throws Exception {
    long id = 1;
    CLIENT.getInboundShipment(supplierId, id);
  }

  @Test
  public void getInboundShipmentCount() throws Exception {
    Result<PurchaseOrderClientResult> result = CLIENT.getInboundShipmentCount(supplierId);
    assertTrue(result.getResult().getTotalCount() > 0);
  }

  @Test
  public void getInboundShipments() throws Exception {
    List<InboundShipmentCriteria.Filter> filters = Lists.newArrayList();
    filters.add(new InboundShipmentCriteria.Filter(Field.STATUS, "COMPLETE"));
    InboundShipmentCriteria criteria = new InboundShipmentCriteria.Builder(supplierId).paginate(1, 4).filter(filters)
        .sort(Field.LAST_ACTIVITY_DATE, OrderBy.ASC).build();
    Result<PurchaseOrderClientResult> result = CLIENT.getInboundShipments(criteria);
    assertEquals(4, result.getResult().getProcessedInboundShipmentMessage().getProcessedInboundShipments().size());
  }

  @Test
  public void multipleFiltersAndSorts() throws GatewayException {

    InboundShipmentQuery inboundShipmentQuery = new InboundShipmentQuery();
    inboundShipmentQuery.setPageSize(20);
    inboundShipmentQuery.setPage(0);

    SingleFilter<InboundShipmentQuery.Filters, String> poSearchFilter =
        new SingleFilter.SingleFilterBuilder<InboundShipmentQuery.Filters, String>()
            .where(InboundShipmentQuery.Filters.PO_NUMBER).startsWith("P1").build();

    inboundShipmentQuery.setFilter(poSearchFilter);

    List<Status> statuses = Lists.newArrayList(REJECTED, ACCEPTED);
    MultiValueFilter<InboundShipmentQuery.Filters, Status> statusFilterExpression =
        new MultiValueFilter.MultiValueFilterBuilder<InboundShipmentQuery.Filters, Status>()
            .where(InboundShipmentQuery.Filters.STATUS).contains(statuses).build();

    inboundShipmentQuery.addFilter(LogicalOperator.AND, statusFilterExpression);

    inboundShipmentQuery.sortBy(
        new Sort<>(
            InboundShipmentQuery.Sorts.STATUS,
            Sort.SortOrder.ASC));

    Result<PurchaseOrderClientResult> inboundShipments = CLIENT.search(supplierId, inboundShipmentQuery);
    PurchaseOrderClientResult result = inboundShipments.getResult();
    List<ProcessedInboundShipmentType> processedInboundShipments =
        result.getProcessedInboundShipmentMessage().getProcessedInboundShipments();

    assertTrue(processedInboundShipments.size() > 0);
    assertTrue(result.getTotalCount() > 0);
  }

  @Test
  public void getInboundShipmentBySupplierIdAndDateRangeForCsv() throws Exception {
    ClientResponse response = CLIENT.getInboundShipments(supplierId, GenericClient.toDate("2013-11-27T14:00:44"),
      GenericClient.toDate("2013-11-27T14:00:44"), MimeType.CSV);
    assertEquals(200, response.getStatus());
    String csv = IOUtils.toString(response.getEntityInputStream());
    assertTrue(csv.contains("385"));
    assertTrue(csv.contains("P130800022-C"));
    // TODO: validate more information in csv
  }

  @Test
  public void getInboundShipmentBySupplierIdAndDateRangeForCsv_NoResults() throws Exception {
    Id<SupplierId> foo = new Id<>(2);
    ClientResponse response = CLIENT.getInboundShipments(foo, GenericClient.toDate("2015-02-09T00:00:00"),
      GenericClient.toDate("2015-02-09T23:59:59"), MimeType.CSV);
    assertEquals(404, response.getStatus());
  }

  @Test
  public void getInboundShipmentBySupplierIdAndDateRange() throws Exception {
    Result<ProcessedInboundShipmentMessage> result = CLIENT.getInboundShipments(supplierId,
      GenericClient.toDate("2013-11-27T14:00:44"), GenericClient.toDate("2013-11-27T14:00:44"));
    assertEquals(200, result.getStatus());
    ProcessedInboundShipmentMessage inboundShipments = result.getResult();
    assertEquals(1, inboundShipments.getProcessedInboundShipments().size());
    ProcessedInboundShipmentType inboundShipment = inboundShipments.getProcessedInboundShipments().get(0);
    assertEquals("P130800022-C", inboundShipment.getInboundShipmentNumber());
    assertEquals(4, inboundShipment.getProcessedInboundShipmentLines().size());
    for (ProcessedInboundShipmentLineType line : inboundShipment.getProcessedInboundShipmentLines()) {
      if (line.getLineNumber() == 1) {
        assertEquals(1, line.getProcessedInboundShipmentLineReceipts().size());
      }
      else if (line.getLineNumber() == 2) {
        assertEquals(6, line.getProcessedInboundShipmentLineReceipts().size());
      }
      else if (line.getLineNumber() == 3) {
        assertEquals(2, line.getProcessedInboundShipmentLineReceipts().size());
      }
      else {
        assertEquals(1, line.getProcessedInboundShipmentLineReceipts().size());
      }
    }
  }

  @Test
  public void createInboundShipmentCsv() throws Exception {
    long name = CLIENT.getInboundShipmentCount(supplierId).getResult().getTotalCount() + 1;
    StringBuilder csv = new StringBuilder();
    csv.append("Inbound Shipment Number,Warehouse Name,Estimated Arrival Date,Line Number,Partner SKU,Barcode,Quantity Ordered,Product Unit Of Measure,Quantity Per Carton,Number Of Cartons");
    csv.append("\n");
    csv.append(String.valueOf(name) + ",CPA,2014-06-12-12:00,1,5448S,,40,EACH,1,1");

    ClientResponse response = CLIENT.createOrUpdateInboundShipment(supplierId, IOUtils.toInputStream(csv.toString()),
      MimeType.CSV, MimeType.CSV);
    String csvResponse = IOUtils.toString(response.getEntityInputStream());
    assertTrue(
      csvResponse,
      csvResponse
          .contains("Status,Error Message,Inbound Shipment Number,List Status,List Error Message,Line Number,Line Status,Line Error Message"));
    assertTrue(csvResponse, csvResponse.contains("SUCCESS,," + String.valueOf(name) + ",SUCCESS,,1,SUCCESS,"));
  }

  @Test
  public void createInboundShipment() throws Exception {
    long name = CLIENT.getInboundShipmentCount(supplierId).getResult().getTotalCount() + 1;
    InboundShipmentMessage message = new InboundShipmentMessage();
    InboundShipmentType type = new InboundShipmentType();
    type.setWarehouseName(DataTypeUtils.toCodeType(new Code<WarehouseName>("CPA")));

    GregorianCalendar c = new GregorianCalendar();
    c.set(2014, 06, 12, 0, 0);
    type.setEstimatedArrivalDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
    type.setInboundShipmentNumber("XML" + String.valueOf(name));
    type.setShipmentStatus(ShipmentStatusType.OPEN);

    InboundShipmentLineType line = new InboundShipmentLineType();
    line.setLineNumber(1);
    line.setPartnerSKU("5448S");
    line.setShipmentStatus(ShipmentStatusType.OPEN);

    ProductUnitOfMeasureType measure = new ProductUnitOfMeasureType();
    measure.setCode("EACH");
    line.setProductUnitOfMeasure(measure);
    line.setQuantityOrdered(1);
    line.setQuantityPerCarton(1L);
    type.getInboundShipmentLines().add(line);
    message.getInboundShipments().add(type);

    Result<InboundShipmentMessageResponse> response = CLIENT.createOrUpdateInboundShipment(supplierId, message);
    InboundShipmentMessageResponse result = response.getResult();
    assertEquals(InboundShipmentMessageStatusType.SUCCESS, result.getStatus());
    assertEquals(1, result.getInboundShipmentResponseLists().size());
    InboundShipmentResponseType actualType = result.getInboundShipmentResponseLists().get(0);
    assertEquals(type.getInboundShipmentNumber(), actualType.getInboundShipmentNumber());
    assertEquals(InboundShipmentStatusType.SUCCESS, actualType.getStatus());
    assertEquals(1, actualType.getInboundShipmentLineResponseLists().size());
    InboundShipmentLineResponseType actualLine = actualType.getInboundShipmentLineResponseLists().get(0);
    assertEquals(line.getLineNumber(), actualLine.getLineNumber());
    assertEquals(InboundShipmentLineStatusType.SUCCESS, actualLine.getStatus());
  }

  @Test
  public void getWarehouseByRetailChannelCode() throws Exception {
    WarehouseList list = CLIENT.getWarehousesByRetailChannelCode("BEST_SELLING_OSTK");
    assertTrue(list.getTotalCount() > 0);
  }

  @Test(expected = GatewayException.DoesNotExist.class)
  public void getWarehouseByRetailChannelCode404() throws Exception {
    CLIENT.getWarehousesByRetailChannelCode("foo");
  }

  @Test
  public void getWarehousesBySupplierId() throws Exception {
    WarehouseList list = CLIENT.getWarehousesBySupplierId(new Id<SupplierId>(2));
    assertTrue(list.getTotalCount() > 0);
  }

  @Test(expected = GatewayException.DoesNotExist.class)
  public void getWarehousesBySupplierId404() throws Exception {
    CLIENT.getWarehousesBySupplierId(new Id<SupplierId>(0));
  }

  // TODO: remove this integration test since is not a good way to execute it
  // and make sure we have very good unit test coverage.
  @Ignore()
  @Test
  public void saveSalesChannelWarehouseIdForWarehouse() throws Exception{
    Id<SalesChannelWarehouseLinkId> salesChannelWarehouseLinkId = new Id<>(1L);
    Code<SalesChannelWarehouseCode> salesChannelWarehouseCode = new Code<>("testcode");

    SalesChannelWarehouse actual =
        CLIENT.saveSalesChannelWarehouseIdForWarehouse(salesChannelWarehouseLinkId, salesChannelWarehouseCode);
    assertThat(actual, notNullValue());
    assertThat(actual.getId(), equalTo(salesChannelWarehouseLinkId));
    assertThat(actual.getSalesChannelWarehouseCode(), equalTo(salesChannelWarehouseCode));
  }

  @Test(expected = GatewayException.DoesNotExist.class)
  public void saveSalesChannelWarehouseIdForWarehouse404() throws Exception {
    CLIENT.saveSalesChannelWarehouseIdForWarehouse(
      new Id<SalesChannelWarehouseLinkId>(0L), new Code<SalesChannelWarehouseCode>("testcode"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void getWarehouseByCriteria() throws Exception {
    Code<WarehouseName> name = new Code<WarehouseName>("IML");
    WarehouseCriteria criteria = WarehouseCriteria.builder().addNames(name).build();

    ImmutableList<Warehouse> warehouseList = CLIENT.getWarehouseBy(criteria);
    assertThat(warehouseList, notNullValue());
    assertThat(warehouseList.size(), equalTo(1));
    assertThat(warehouseList.get(0).getName(), equalTo(name));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void getWarehouseByCriteria_NotFound() throws Exception {
    Code<WarehouseName> name = new Code<WarehouseName>("BAR");
    WarehouseCriteria criteria = WarehouseCriteria.builder().addNames(name).build();

    ImmutableList<Warehouse> warehouseList = CLIENT.getWarehouseBy(criteria);
    assertThat(warehouseList, notNullValue());
    assertTrue(warehouseList.isEmpty());
  }

  @Test
  public void getWarehouseByName() throws Exception {
    String name = "IML";
    Warehouse warehouse = CLIENT.getWarehouse(name);
    assertThat(warehouse.getName(), equalTo(new Code<WarehouseName>(name)));
  }

  @Test(expected = GatewayException.DoesNotExist.class)
  public void getWarehouseByName_NotFound() throws Exception {
    CLIENT.getWarehouse("BAR");
  }

  @Test
  @Ignore
  public void createWarehouse() throws Exception {
    ImmutableList<Warehouse> list = CLIENT.getWarehouseBy(WarehouseCriteria.builder().build());
    CLIENT.createWarehouse(createWarehouse(createWarehouseName(list)), supplierId, new Id<SalesChannelId>(1));
  }

  private String createWarehouseName(List<Warehouse> warehouseList) {
    return foo(warehouseList, 0);
  }

  private String foo(List<Warehouse> warehouseList, int index) {
    for (Warehouse warehouse : warehouseList) {
      if (warehouse.getName().toString().equals(fooName(index))) {
        index = index + 1;
        return foo(warehouseList, index);
      }
    }
    return fooName(index);
  }

  private String fooName(int index) {
    return "FOO" + index;
  }

  private Warehouse createWarehouse(String name) {
    Warehouse warehouse = new Warehouse();
    warehouse.setDisplayName("WarehouseResourceTest");
    warehouse.setName(new Code<WarehouseName>(name));
    warehouse.setStatus(com.overstock.partnergateway.core.warehouse.api.Warehouse.Status.ACTIVE);
    warehouse.setType(WarehouseType.OSTK);
    warehouse.setPhysicalAddress(createAddress());
    warehouse.setContactAddress(createAddress());
    return warehouse;
  }

  private Address createAddress() {
    Address address = new Address();
    address.setAddressee("PGDEV1");
    address.setAltPhoneNumber("911");
    address.setCity("SLC");
    address.setCountryCode("US");
    address.setLineOne("Line1");
    address.setState("Utah");
    address.setPostalCode("84119");
    return address;
  }
}
