package com.joe.cms.parcel.service;

import com.joe.cms.parcel.dto.ItemDTO;
import com.joe.cms.parcel.model.Item;
import com.joe.cms.parcel.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepo itemRepo;

    @Autowired
    public ItemService(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    public List<ItemDTO> listAllItems() {
        return itemRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ItemDTO findItemById(Long id) {
        Item item = itemRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Item Not Found In Records.."));
        return convertToDTO(item);
    }

    @Transactional
    public ItemDTO createItem(ItemDTO itemDTO) {
        // Check for existing item with the same name (case insensitive)
        Item existingItem = itemRepo.findByNameIgnoreCase(itemDTO.getItemName());
        if (existingItem != null) {
            throw new IllegalArgumentException("An item with the same name already exists.");
        }

        // If no duplicate, proceed to create the new item
        Item item = convertToEntity(itemDTO);
        Item savedItem = itemRepo.save(item);
        return convertToDTO(savedItem);
    }

    @Transactional
    public ItemDTO updateItem(ItemDTO itemDTO) {
        Item item = itemRepo.findById(itemDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Item Not Found"));
        item.setItemType(itemDTO.getItemType());
        item.setItemName(itemDTO.getItemName());
        item.setItemDescription(itemDTO.getItemDescription());
       // item.setWeight(itemDTO.getWeight());
        Item updatedItem = itemRepo.save(item);
        return convertToDTO(updatedItem);
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }

    private ItemDTO convertToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setItemType(item.getItemType());
        itemDTO.setItemName(item.getItemName());
        itemDTO.setItemDescription(item.getItemDescription());
        //itemDTO.setWeight(item.getWeight());
        itemDTO.setCreatedAt(item.getCreatedAt());
        itemDTO.setUpdatedAt(item.getUpdatedAt());
        itemDTO.setCreatedBy(item.getCreatedBy());
        itemDTO.setUpdatedBy(item.getUpdatedBy());
        return itemDTO;
    }

    private Item convertToEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setItemType(itemDTO.getItemType());
        item.setItemName(itemDTO.getItemName());
        item.setItemDescription(itemDTO.getItemDescription());
       // item.setWeight(itemDTO.getWeight());
        item.setCreatedAt(itemDTO.getCreatedAt());
        item.setUpdatedAt(itemDTO.getUpdatedAt());
        item.setCreatedBy(itemDTO.getCreatedBy());
        item.setUpdatedBy(itemDTO.getUpdatedBy());
        return item;
    }
}
