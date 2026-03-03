package com.booking.resource_service.service;

import com.booking.resource_service.model.Resource;
import com.booking.resource_service.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public List<Resource> getAvailableResources() {
        return resourceRepository.findByAvailable(true);
    }

    public Resource createResource(Resource resource) {
        resource.setAvailable(true);
        return resourceRepository.save(resource);
    }

    public Resource getResourceById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    public Resource updateResource(Long id, Resource updatedResource) {
        Resource existing = getResourceById(id);
        existing.setName(updatedResource.getName());
        existing.setDescription(updatedResource.getDescription());
        existing.setType(updatedResource.getType());
        existing.setAvailable(updatedResource.isAvailable());
        return resourceRepository.save(existing);
    }

    public void deleteResource(Long id) {
        getResourceById(id);
        resourceRepository.deleteById(id);
    }
}
