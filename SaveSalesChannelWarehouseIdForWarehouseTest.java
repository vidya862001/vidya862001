package com.overstock.partnergateway.warehouse.coordination.web.rest;

import com.overstock.framework.Bordello;
import com.overstock.model.Code;
import com.overstock.model.Id;
import com.overstock.partnergateway.core.client.Result;
import com.overstock.partnergateway.core.common.codes.SalesChannelWarehouseCode;
import com.overstock.partnergateway.core.common.ids.SalesChannelWarehouseLinkId;
import com.overstock.partnergateway.core.warehouse.api.SalesChannelWarehouse;
import com.overstock.partnergateway.warehouse.coordination.common.WarehouseException;
import com.overstock.partnergateway.warehouse.coordination.service.WarehouseService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class SaveSalesChannelWarehouseIdForWarehouseTest extends ResourceTest {
  
  @Mock
  private WarehouseService service;
  
  @Before
  public void before() {
    service = Mockito.mock(WarehouseService.class);
    Bordello.setGlobally(WarehouseService.class, service);
    Bordello.set(WarehouseService.class, service);
  }  
  
  @Test
  public void saveSalesChannelWarehouseIdForWarehouse() {
    Id<SalesChannelWarehouseLinkId> salesChannelWarehouseLinkId = new Id<>(1L);
    Code<SalesChannelWarehouseCode> salesChannelWarehouseCode = new Code<>("testcode");
    Result<SalesChannelWarehouse> result = new Result<>();
    SalesChannelWarehouse salesChannelWarehouse = new SalesChannelWarehouse();
    result.setResult(salesChannelWarehouse);

    when(service.saveSalesChannelWarehouseIdForWarehouse(salesChannelWarehouseLinkId, salesChannelWarehouseCode))
        .thenReturn(result);

    Response response = target().path(
      String.format("/saleschannelwarehouses/%s/saleschannelwarehousecode/%s",
        salesChannelWarehouseLinkId, salesChannelWarehouseCode))
        .request(MediaType.APPLICATION_XML).put(Entity.entity("", MediaType.APPLICATION_XML));

    try {
      assertThat(response, is(not(nullValue())));
      assertThat(response.getStatus(), is(equalTo(200)));
      assertThat(response.readEntity(SalesChannelWarehouse.class), is(equalTo(result.getResult())));
    } finally {
      if (response != null) response.close();
    }
    verify(service).saveSalesChannelWarehouseIdForWarehouse(salesChannelWarehouseLinkId, salesChannelWarehouseCode);
    verifyNoMoreInteractions(service);
  }
  
  @Test
  public void saveSalesChannelWarehouseIdForWarehouse_NotFound() {
    Id<SalesChannelWarehouseLinkId> salesChannelWarehouseLinkId = new Id<>(0L);
    Code<SalesChannelWarehouseCode> salesChannelWarehouseCode = new Code<>("testcode");

    when(service.saveSalesChannelWarehouseIdForWarehouse(salesChannelWarehouseLinkId, salesChannelWarehouseCode))
        .thenThrow(new WarehouseException.DoesNotExist("No Found"));

    Response response = target().path(
      String.format("/saleschannelwarehouses/%s/saleschannelwarehousecode/%s",
        salesChannelWarehouseLinkId, salesChannelWarehouseCode))
        .request(MediaType.APPLICATION_XML).put(Entity.entity("", MediaType.APPLICATION_XML));

    try {
      assertThat(response, is(not(nullValue())));
      assertThat(response.getStatus(), is(equalTo(404)));
    } finally {
      if (response != null) response.close();
    }
    
    verify(service).saveSalesChannelWarehouseIdForWarehouse(salesChannelWarehouseLinkId, salesChannelWarehouseCode);
    verifyNoMoreInteractions(service);
  }
}
