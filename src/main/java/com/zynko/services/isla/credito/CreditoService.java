package com.zynko.services.isla.credito;

import com.zynko.dtos.isla.creditos.CreditoFormularioDTO;
import com.zynko.dtos.isla.creditos.CreditoProductoRequest;
import com.zynko.dtos.isla.creditos.CreditoRequest;
import com.zynko.dtos.isla.creditos.CreditoResponseDTO;
import com.zynko.models.isla.*;
import com.zynko.models.isla.credito.Cliente;
import com.zynko.models.isla.credito.Credito;
import com.zynko.models.isla.credito.CreditoProducto;
import com.zynko.models.isla.credito.Producto;
import com.zynko.repositories.isla.credito.ClienteRepository;
import com.zynko.repositories.isla.credito.CreditoRepository;
import com.zynko.repositories.isla.credito.ProductoRepository;
import com.zynko.services.isla.TurnoIslaService;
import org.springframework.beans.factory.annotation.Autowired;
import com.zynko.mappers.isla.CreditoMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditoService {

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private TurnoIslaService turnoIslaService;

    @Autowired
    private CreditoMapper creditoMapper;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository; // Inyectar el repositorio de Cliente

    public List<Credito> buscarTodos() {
        return creditoRepository.findAll();
    }

    public Credito buscarPorId(Long id) {
        return creditoRepository.findById(id).orElseThrow(() -> new RuntimeException("Credito no encontrado con id: " + id));
    }

    public Credito guardar(Credito credito) {
        return creditoRepository.save(credito);
    }

    public void eliminarPorId(Long id) {
        creditoRepository.deleteById(id);
    }

    @Transactional
    public CreditoResponseDTO actualizar(Long id, CreditoRequest request) {

        Credito credito = creditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credito no encontrado con id: " + id));

        // --- Lógica para actualizar la colección de items desde el DTO ---
        if (request.getItems() != null) {
            // 1. Limpiar la colección existente. JPA borrará los registros hijos gracias a orphanRemoval=true.
            credito.getItems().clear();

            // 2. Iterar sobre los nuevos items que vienen en el DTO.
            for (CreditoProductoRequest itemRequest : request.getItems()) {

                // 3. Cargar la entidad Producto real desde la BD.
                Producto productoReal = productoRepository.findById(itemRequest.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + itemRequest.getProductoId()));

                // 4. Crear una nueva entidad CreditoProducto
                CreditoProducto newItemEntidad = new CreditoProducto();
                newItemEntidad.setProducto(productoReal);
                newItemEntidad.setPrecioVenta(itemRequest.getPrecioVenta());
                newItemEntidad.setCredito(credito); // Establecer la relación bidireccional

                // 5. Añadir el nuevo item a la colección gestionada del crédito.
                credito.getItems().add(newItemEntidad);
            }
        }

        // Recalcular el total basado en los nuevos items
        int totalCalculado = credito.getItems().stream()
                .mapToInt(item -> item.getPrecioVenta() != null ? item.getPrecioVenta() : 0)
                .sum();
        credito.setTotal(totalCalculado);

        // Como el método es @Transactional, JPA guardará los cambios automáticamente al final.
        // La llamada a save() es opcional pero clarifica la intención.
        Credito creditoActualizado = creditoRepository.save(credito);

        return creditoMapper.toResponse(creditoActualizado);
    }

    public List<Credito> buscarCreditosPorTurno() {

        TurnoIsla turnoIsla = turnoIslaService.getTurnoActivo();

        return creditoRepository.findAllByTurnoIslaId(turnoIsla.getId());
    }

    @Transactional
    public CreditoResponseDTO crearCredito(CreditoRequest creditoRequest) {
        TurnoIsla turnoIsla = turnoIslaService.getTurnoActivo();

        Credito credito = creditoMapper.toEntity(creditoRequest);

        credito.setTurnoIsla(turnoIsla);


        Cliente clienteReal = clienteRepository.findById(creditoRequest.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + creditoRequest.getClienteId()));
        credito.setCliente(clienteReal);

        int totalCalculado = 0;
        if (credito.getItems() != null && !credito.getItems().isEmpty()) {
            // 3. Iterar para asignar el producto correcto
            for (int i = 0; i < credito.getItems().size(); i++) {
                CreditoProducto itemEntidad = credito.getItems().get(i);
                CreditoProductoRequest itemRequest = creditoRequest.getItems().get(i);

                // 4. Buscar el producto real en la BD
                Producto productoReal = productoRepository.findById(itemRequest.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + itemRequest.getProductoId()));

                // 5. Asignar la entidad de producto gestionada por JPA
                itemEntidad.setProducto(productoReal);
                itemEntidad.setCredito(credito); // Establecer la relación bidireccional

                if (itemEntidad.getPrecioVenta() != null) {
                    totalCalculado += itemEntidad.getPrecioVenta();
                }
            }
        }

        credito.setTotal(totalCalculado);
        credito.setFecha(LocalDateTime.now());

        Credito creditoGuardado = creditoRepository.save(credito);

        return creditoMapper.toResponse(buscarPorId(creditoGuardado.getId()));
    }

    public CreditoFormularioDTO getDatosParaFormulario() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<Producto> productos = productoRepository.findAll();

        return new CreditoFormularioDTO(clientes, productos);
    }

    @Transactional
    public CreditoResponseDTO eliminar(Long id) {

        Credito credito = creditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credito no encontrado con id: " + id));


        credito.getItems().clear();
        credito.setTotal(0);
        Credito creditoActualizado = creditoRepository.save(credito);

        return creditoMapper.toResponse(creditoActualizado);
    }
}

