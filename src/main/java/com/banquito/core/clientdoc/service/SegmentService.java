package com.banquito.core.clientdoc.service;

import com.banquito.core.clientdoc.model.Segment;
import com.banquito.core.clientdoc.repository.SegmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SegmentService {

    private final SegmentRepository repository;
    private static final String CLIENT_SEGMENT_NOT_FOUND = "No existe el segmento con código: ";

    public SegmentService(SegmentRepository repository) {
        this.repository = repository;
    }

    public List<Segment> getAllSegments() {
        return repository.findAllByOrderByName();
    }

    public Segment getSegmentByCode(String code) {
        Optional<Segment> segmentOpt = repository.findById(code);
        if (segmentOpt.isPresent()) {
            return segmentOpt.get();
        } else {
            throw new RuntimeException(CLIENT_SEGMENT_NOT_FOUND + code);
        }
    }

    public Segment createSegment(Segment segment) {
        return repository.save(segment);
    }

    public Segment updateSegment(String code, Segment segmentDetails) {
        Optional<Segment> optionalSegment = repository.findById(code);
        if (optionalSegment.isPresent()) {
            Segment segment = optionalSegment.get();
            segment.setName(segmentDetails.getName());
            segment.setClientType(segmentDetails.getClientType());
            segment.setDescription(segmentDetails.getDescription());
            return repository.save(segment);
        } else {
            throw new RuntimeException(CLIENT_SEGMENT_NOT_FOUND + code);
        }
    }

    public void deleteSegment(String code) {
        Optional<Segment> optionalSegment = repository.findById(code);
        if (optionalSegment.isPresent()) {
            repository.delete(optionalSegment.get());
        } else {
            throw new RuntimeException(CLIENT_SEGMENT_NOT_FOUND + code);
        }
    }
}
