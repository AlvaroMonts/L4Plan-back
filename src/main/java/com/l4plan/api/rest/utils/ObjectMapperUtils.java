package com.l4plan.api.rest.utils;

import com.l4plan.api.rest.dto.*;
import com.l4plan.api.rest.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

import java.util.*;
import java.util.stream.Collectors;

public class ObjectMapperUtils {

    private static final ModelMapper modelMapper;

    /**
     * Model mapper property setting are specified in the following block.
     * Default property matching strategy is set to Strict see {@link MatchingStrategies}
     * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
     */
    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        addMappings();
    }

    public ObjectMapperUtils() {
    }

    /**
     * Note: outClass object must have default constructor with no arguments
     *
     * @param <D>      type of result object.
     * @param <T>      type of source object to map from.
     * @param entity   entity that needs to be mapped.
     * @param outClass class of result object.
     * @return new object of <code>outClass</code> type.
     */
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    /**
     * Note: outClass object must have default constructor with no arguments
     *
     * @param entityList list of entities that needs to be mapped
     * @param outCLass   class of result list element
     * @param <D>        type of objects in result list
     * @param <T>        type of entity in <code>entityList</code>
     * @return list of mapped object with <code><D></code> type.
     */
    public static <D, T> List<D> mapList(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    /**
     * Maps {@code source} to {@code destination}.
     *
     * @param source      object to map from
     * @param destination object to map to
     */
    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }

    private static void addMappings() {
        // Plan to PlanDTO
        modelMapper.typeMap(Plan.class, PlanDTO.class)
                .addMapping(src -> src.getUser().getId(), PlanDTO::setUserId);

        // PlanDTO to Plan
        modelMapper.typeMap(PlanDTO.class, Plan.class)
                .addMappings(mapper -> mapper.skip(Plan::setUser))
                .setPostConverter(context -> {
                    PlanDTO dto = context.getSource();
                    Plan entity = context.getDestination();
                    if (dto.getUserId() != null) {
                        entity.setUser(new User(dto.getUserId()));
                    }
                    return entity;
                });

        // PlanSitesGoogle to PlanSitesGoogleDTO
        modelMapper.typeMap(PlanSitesGoogle.class, PlanSitesGoogleDTO.class)
                .addMapping(src -> src.getPlan().getId(), PlanSitesGoogleDTO::setPlanId);

        // PlanSitesGoogleDTO to PlanSitesGoogle
        modelMapper.typeMap(PlanSitesGoogleDTO.class, PlanSitesGoogle.class)
                .addMappings(mapper -> mapper.skip(PlanSitesGoogle::setPlan))
                .setPostConverter(context -> {
                    PlanSitesGoogleDTO dto = context.getSource();
                    PlanSitesGoogle entity = context.getDestination();
                    if (dto.getPlanId() != null) {
                        entity.setPlan(new Plan(dto.getPlanId()));
                    }
                    return entity;
                });
    }
}